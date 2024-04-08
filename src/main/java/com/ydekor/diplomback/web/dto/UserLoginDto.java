package com.ydekor.diplomback.web.dto;

public record UserLoginDto(SpaUserDto userData, String token, String refreshToken) {
}