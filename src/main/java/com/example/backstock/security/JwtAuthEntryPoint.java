package com.example.backstock.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String errorMessage = "";
        if (authException instanceof BadCredentialsException || authException instanceof UsernameNotFoundException) {
            errorMessage = "Bad credentials.";
        } else {
            switch (authException.getClass().getSimpleName()) {
                case "DisabledException":
                    errorMessage = "Account disabled.";
                    break;
                case "InsufficientAuthenticationException":
                    errorMessage = "Access token invalid.";
                    break;
                default:
                    errorMessage = "Error.";
                    break;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(errorMessage);
    }
}
