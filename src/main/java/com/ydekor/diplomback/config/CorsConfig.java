package com.ydekor.diplomback.config;

import com.ydekor.diplomback.config.properties.CorsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
public class CorsConfig extends UrlBasedCorsConfigurationSource {
    public CorsConfig(CorsProperties corsConfig) {
        if (corsConfig.getOrigins().isEmpty())
            log.error("Parameter 'application.allowed.origins' is EMPTY");

        log.info("Ser cors origins: "+ corsConfig.getOrigins());
        log.info("Ser cors methods: "+ corsConfig.getMethods());
        log.info("Ser cors headers: "+ corsConfig.getHeaders());
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(corsConfig.getOrigins().split(",")));
        configuration.setAllowedMethods(Arrays.asList(corsConfig.getMethods().split(",")));
        configuration.setAllowedHeaders(Arrays.asList(corsConfig.getHeaders().split(",")));

        this.registerCorsConfiguration("/**", configuration);
    }
}
