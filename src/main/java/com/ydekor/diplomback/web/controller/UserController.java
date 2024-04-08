package com.ydekor.diplomback.web.controller;

import com.ydekor.diplomback.service.auth.AuthenticationService;
import com.ydekor.diplomback.service.auth.UserService;
import com.ydekor.diplomback.web.dto.SpaUserDto;
import com.ydekor.diplomback.web.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends ExceptionHandlerController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PutMapping
    public SpaUserDto register(@RequestBody SpaUserDto dto) {
        return userService.create(dto);
    }


    @PostMapping("/login")
    public UserLoginDto signIn(@RequestBody SpaUserDto inDto) {
        return authenticationService.signInByLoginAndPassword(inDto.getLogin(), inDto.getPassword());
    }

    @PostMapping("/update-token")
    public UserLoginDto updateToken() {
        return authenticationService.updateTokens();

    }

}
