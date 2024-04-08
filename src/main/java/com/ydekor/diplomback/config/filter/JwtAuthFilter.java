package com.ydekor.diplomback.config.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydekor.diplomback.config.properties.JwtProperties;
import com.ydekor.diplomback.web.dto.ResponseError;
import com.ydekor.diplomback.exception.SimpleException;
import com.ydekor.diplomback.service.auth.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final AuthenticationService authenticationService;

    private void writeErrorMessage(HttpServletResponse rs, Exception e) {
        try {
            log.error(e.getMessage(), e);
            rs.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            new ObjectMapper().writeValue(rs.getOutputStream(), new ResponseError(new SimpleException(e.getMessage())));
        } catch (Exception ex) {
            log.error("Cannot write error message to output stream", ex);
        }
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest rq,
                                    @NonNull HttpServletResponse rs,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // get Authorization header
        final String authHeader = rq.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(jwtProperties.getTokenPrefix())) {
            filterChain.doFilter(rq, rs);
            return;
        }

        String token = authHeader.substring(jwtProperties.getTokenPrefix().length());

        try {
            UserDetails user = authenticationService.findUserByToken(token);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    null,
                    user.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(rq)); // add remote ip address
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (TokenExpiredException | JWTDecodeException ex) {
            writeErrorMessage(rs, ex);
//        } catch (BadRequestException ex) {
//            writeErrorMessage(rs, new UsernameNotFoundException(ex.getMessage()));
        } catch (Exception e) {
            log.error("Unknown error: {}", e.getMessage());
            e.printStackTrace();
            writeErrorMessage(rs, e);
        }

        // go to next filter
        filterChain.doFilter(rq, rs);
    }

}
