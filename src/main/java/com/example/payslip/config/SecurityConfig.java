package com.example.payslip.config;


import com.example.payslip.config.filter.response.ResponseFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain employeeSecurityFilterChain(HttpSecurity http) throws Exception {

        Filter employeeFilter = new AuthFilter(authService);
        Filter responseFilter = new ResponseFilter(objectMapper);

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .addFilterAfter(responseFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(employeeFilter, ResponseFilter.class);

        return http.build();
    }

}
