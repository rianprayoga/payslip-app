package com.example.payslip.config.filter.authentication;

import com.example.payslip.config.User;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AdminAuthentication extends UsernamePasswordAuthenticationToken {

    @Getter
    private final User user;

    public AdminAuthentication(
            User user, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.user = user;
    }
}