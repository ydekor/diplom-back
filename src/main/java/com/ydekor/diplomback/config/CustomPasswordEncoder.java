package com.ydekor.diplomback.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder extends BCryptPasswordEncoder {
    public CustomPasswordEncoder() {
        super(12);
    }
}
