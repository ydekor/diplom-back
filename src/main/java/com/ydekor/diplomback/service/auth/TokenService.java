package com.ydekor.diplomback.service.auth;

import com.auth0.jwt.JWT;
import com.ydekor.diplomback.config.properties.JwtProperties;
import com.ydekor.diplomback.model.user.SpaRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProperties jwtProperties;

    public String generateToken(String userName, List<SpaRole> userRoles, boolean isRefreshToken) {
        StringBuilder sb = new StringBuilder(String.format("Generate token with parameters: userName='%s', isRefreshToken='%s' => ", userName, isRefreshToken));
        try {
            Date expireDate = new Date(System.currentTimeMillis() + (long) (isRefreshToken ? 100 : 1) * jwtProperties.getTokenExpiration() * 60 * 1000);

            List<String> rolesList = userRoles.stream()
                    .map(SpaRole::getName)
                    .collect(Collectors.toList());

            String token = jwtProperties.getTokenPrefix() + JWT
                    .create()
                    .withSubject(userName)
                    .withExpiresAt(expireDate)
//                            .withIssuer(issuer)
                    .withClaim("roles", rolesList)
                    .sign(jwtProperties.getAlgorithm());

            log.trace(sb.append("Success").toString());
            return token;
        } catch (Exception e) {
            log.error(sb.append("Error: ").append(e.getMessage()).toString());
            throw new RuntimeException(e.getMessage());
        }
    }
}
