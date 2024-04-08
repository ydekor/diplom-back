package com.ydekor.diplomback.service.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ydekor.diplomback.config.properties.JwtProperties;
import com.ydekor.diplomback.web.dto.UserLoginDto;
import com.ydekor.diplomback.exception.BadRequestException;
import com.ydekor.diplomback.web.mapper.SpaUserMapper;
import com.ydekor.diplomback.model.user.SpaUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SpaUserMapper spaUserMapper;
    private final TokenService tokenService;
    private final JwtProperties jwtProperties;

    private UserLoginDto generateTokens(SpaUser user) {
        return new UserLoginDto(
                spaUserMapper.sourceToDto(user),
                tokenService.generateToken(user.getLogin(), user.getRoles(), false),
                tokenService.generateToken(user.getLogin(), user.getRoles(), true)
        );
    }

    @Transactional
    public UserLoginDto signInByLoginAndPassword(String login, String password) {
        SpaUser user = userService.getUserByLogin(login);

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadRequestException("password");

        userService.updateUserLastLogin(user);

        return generateTokens(user);
    }

    public UserDetails findUserByToken(String token) {
        // decode & verify expire date token
        DecodedJWT decodedJWT = jwtProperties
                .getVerifier()
                .verify(token);

        // get username from token
        String username = decodedJWT.getSubject();
        return userService.getUserByLogin(username);
    }

    public UserLoginDto updateTokens() {
        return generateTokens(getCurrentUser());
    }

    public SpaUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByLogin(authentication.getName());
    }

}
