package com.ydekor.diplomback.config;

import com.ydekor.diplomback.config.filter.JwtAuthFilter;
import com.ydekor.diplomback.config.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final CorsProperties corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())    // defaults use bean "corsConfigurationSource"
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/user/login",
                                "/user/emailConfirm",
                                "/user",
                                "/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**"
                        ).permitAll()

                        .requestMatchers("/admin-user", "/admin-user/*").hasAnyAuthority("ADMIN", "OWNER")
                        .requestMatchers("/phone","/phone/**", "phone/sms/**").hasAnyAuthority("PAGE_PHONE")
                        .requestMatchers("/api/feeding/*").hasAnyAuthority("PAGE_FEEDING")
                        .requestMatchers("/info", "/info/*").hasAnyAuthority("PAGE_INFO")
                        .requestMatchers("/doings-task", "/doings-task/*",
                                "/doings-label", "/doings-label/*",
                                "/doings-log", "/doings-log/*"
                                ).hasAnyAuthority("PAGE_DOINGS")
                        .requestMatchers(//"/cash-wallet", "/cash-wallet/*",
                                "/cash-transaction", "/cash-transaction/*",
                                "/cash-report", "/cash-report/*"
                                ).hasAnyAuthority("PAGE_CASH")
                        .requestMatchers(
                                "/cooking-category", "/cooking-category/*",
//                                "/cooking-dish", "/cooking-dish/*",
                                "/cooking-receipt", "/cooking-receipt/*"
                                ).hasAnyAuthority("PAGE_COOKING")

                        .anyRequest().authenticated()
                ).build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfig(corsConfig);
    }
}
