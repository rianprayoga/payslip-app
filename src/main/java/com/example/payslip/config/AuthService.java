package com.example.payslip.config;

import com.example.payslip.config.filter.authentication.EmployeeAuthentication;
import com.example.payslip.data.entity.EmployeeEntity;
import com.example.payslip.data.repository.EmployeeRepository;
import com.example.payslip.errors.http.UnauthorizedHttpException;
import com.example.payslip.utilities.Base64Codec;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final Base64Codec base64Codec;

    public void verifyCredential(String encodedAuth){
        String decodedCredentials = base64Codec.decode(encodedAuth);

        String[] credentialsParts = decodedCredentials.split(":");
        if (credentialsParts.length != 2) {
            throw new UnauthorizedHttpException("The authorization was malformed.");
        }

        String username = credentialsParts[0];
        String password = credentialsParts[1];

        EmployeeEntity employeeEntity = employeeRepository
                .findPasswordBy(username)
                .orElseThrow(() -> new UnauthorizedHttpException("User %s is unauthorized.".formatted(username)));

        if (!passwordEncoder.matches(password, employeeEntity.getPassword())){
            throw new UnauthorizedHttpException("Unable to authenticate.");
        }

        User credential = new User(employeeEntity.getId(), employeeEntity.getUsername());
        SecurityContextHolder.getContext().setAuthentication(new EmployeeAuthentication(null, credential, List.of()));
    }



    public  <T extends UsernamePasswordAuthenticationToken> T getAuthentication(Class<T> clazz) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authorization has not been set.");
        }

        if (!clazz.isInstance(authentication)){
            throw new UnauthorizedHttpException("Unable to authenticate.");
        }

        return (T) authentication;
    }

}
