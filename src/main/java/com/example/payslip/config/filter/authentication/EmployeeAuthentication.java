package com.example.payslip.config.filter.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class EmployeeAuthentication extends UsernamePasswordAuthenticationToken {


    public EmployeeAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public EmployeeAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
