package com.ydekor.diplomback.config.properties;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtProperties {
    @Getter @Setter
    private String secretKey;

    @Getter @Setter
    private String tokenPrefix;

    @Getter @Setter
    private Integer tokenExpiration;

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey.getBytes());
    }

    public JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }

}