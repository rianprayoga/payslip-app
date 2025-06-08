package com.example.payslip.config;

import com.example.payslip.errors.http.UnauthorizedHttpException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationString = request.getHeader(AUTHORIZATION);
        if (authorizationString == null || authorizationString.isBlank()) {
            throw new UnauthorizedHttpException("The request must provide authorization.");
        }
        String[] authorizationParts = authorizationString.split(" ");
        if (authorizationParts.length != 2) {
            throw new UnauthorizedHttpException("The authorization was malformed.");
        }

        String encodedToken = authorizationParts[1];
        authService.verifyCredential(encodedToken);

        filterChain.doFilter(request, response);

    }
}
