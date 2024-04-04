package com.ydekor.diplomback.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "application.allowed")
public class CorsProperties {
    private String origins = "";
    private String methods = "GET,POST";
    private String headers = "Authorization,Cache-Control,Content-Type";
}
