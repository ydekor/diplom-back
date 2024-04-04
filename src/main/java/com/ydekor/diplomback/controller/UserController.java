package com.ydekor.diplomback.controller;

import com.ydekor.diplomback.dto.UserDto;
import com.ydekor.diplomback.model.User;
import com.ydekor.diplomback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public record UserIdResponse (Long id) {}

    @PostMapping
    public UserIdResponse createUser(@RequestBody UserDto userDto) {
        log.info("create user {}", userDto);

        return new UserIdResponse(userRepository.save(User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build()).getId());
    }

    @GetMapping("/whoareme")
    public UserDto whoAreMe() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =  userRepository
                .findByUsername(currentUserName)
                .orElseThrow(() -> new RuntimeException("user not found"));
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @GetMapping("/byId/{id}")
    public UserDto getUser(@PathVariable Long id) {
        log.info("get user {}", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("record not found"));
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("delete user {}", id);
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("record not found");
        }
        userRepository.deleteById(id);
    }

    @PutMapping
    public Long editUser(@RequestBody UserDto userDto) {
        log.info("edit user {}", userDto);

        if (userDto.getId() == null) {
            throw new RuntimeException("empty id");
        }

        if (!userRepository.existsById(userDto.getId())) {
            throw new RuntimeException("record not found");
        }

        return userRepository.save(User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build()).getId();
    }
}
