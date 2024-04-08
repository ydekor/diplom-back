package com.ydekor.diplomback.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminUserDto {

    private String login;
    private String email;
    private Long created;
    private Long lastLogin;
    private List<String> roles;

}
