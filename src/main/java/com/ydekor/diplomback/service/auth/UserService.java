package com.ydekor.diplomback.service.auth;

import com.ydekor.diplomback.web.dto.SpaUserDto;
import com.ydekor.diplomback.exception.AlreadyExistsException;
import com.ydekor.diplomback.exception.BadRequestException;
import com.ydekor.diplomback.web.mapper.SpaUserMapper;
import com.ydekor.diplomback.model.user.SpaRole;
import com.ydekor.diplomback.model.user.SpaUser;
import com.ydekor.diplomback.model.user.SpaUserStatus;
import com.ydekor.diplomback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SpaUserMapper spaUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public SpaUserDto create(SpaUserDto dto) {
        userRepository
                .findByLogin(dto.getLogin())
                .ifPresent(e -> {
                    throw new AlreadyExistsException(new String[] {"login=" + dto.getLogin()});
                });


        SpaUser user = SpaUser
                .builder()
                .login(dto.getLogin())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .created(Calendar.getInstance())
                .status(SpaUserStatus.CREATED)
                .build();
        roleService.addDefaultRoles(user);

        userRepository.save(user);

        SpaUserDto outDto = spaUserMapper.sourceToDto(user);

        return outDto;
    }

    public SpaUser getUserByLogin(String login) {
        return userRepository
                .findByLogin(login)
                .orElseThrow(() -> new BadRequestException("login='" + login + "'"));
    }

    public void updateUserLastLogin(SpaUser user) {
        user.setLastLogin(Calendar.getInstance());
        userRepository.save(user);
    }

    public SpaUser getUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadRequestException("email='" + email + "'"));
    }

    public List<SpaUser> getAllUsers() {
        return userRepository.findAll();
    }

    public SpaUser addRoleToUser(SpaUser user, SpaRole role) {
        if (user.getRoles().contains(role)) {
            log.debug("User already has role: {}", role.getName());
            return user;
        }
        user.getRoles().add(role);
        log.debug("User after add role: {}", user);
        return userRepository.save(user);
    }

    public SpaUser removeRoleFromUser(SpaUser user, SpaRole role) {
        if (!user.getRoles().contains(role)) {
            log.debug("User not contains role: {}", role.getName());
            return user;
        }
        user.getRoles().remove(role);
        log.debug("User after remove role: {}", user);
        return userRepository.save(user);
    }

}
