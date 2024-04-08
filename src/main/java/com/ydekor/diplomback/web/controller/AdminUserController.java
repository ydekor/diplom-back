package com.ydekor.diplomback.web.controller;

import com.ydekor.diplomback.model.user.SpaUser;
import com.ydekor.diplomback.model.user.SpaRole;
import com.ydekor.diplomback.service.auth.AuthenticationService;
import com.ydekor.diplomback.service.auth.RoleService;
import com.ydekor.diplomback.service.auth.UserService;
import com.ydekor.diplomback.web.dto.AdminUserDto;
import com.ydekor.diplomback.web.dto.UserRoleDto;
import com.ydekor.diplomback.web.mapper.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-user")
public class AdminUserController extends ExceptionHandlerController {

    private final UserService userService;
    private final RoleService roleService;
    private final AdminUserMapper mapper;
    private final AuthenticationService authenticationService;

    protected void logInfo(String message) {
        log.info("[" + authenticationService.getCurrentUser().getLogin() + "]: " + this.getClass().getSimpleName() + ". " + message);
    }

    @GetMapping
    public List<AdminUserDto> getUserList() {
        List<SpaUser> users = userService.getAllUsers();
        return mapper.sourcesToDtos(users);
    }

    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return roleService
                .getAll()
                .stream()
                .map(SpaRole::getName)
                .collect(Collectors.toList());
    }

    @PostMapping("/role")
    public AdminUserDto addRoleToUser(@Validated @RequestBody UserRoleDto userRoleDto) {
        logInfo("Add role to user: " + userRoleDto);
        return mapper.sourceToDto(userService.addRoleToUser(
                userService.getUserByLogin(userRoleDto.getUserLogin()),
                roleService.getByName(userRoleDto.getRoleName())));
    }

    @DeleteMapping("/role")
    public AdminUserDto removeRoleFromUser(@Validated @RequestBody UserRoleDto userRoleDto) {
        logInfo("Remove role from user: " + userRoleDto);

        return mapper.sourceToDto(userService.removeRoleFromUser(
                userService.getUserByLogin(userRoleDto.getUserLogin()),
                roleService.getByName(userRoleDto.getRoleName())));
    }

}
