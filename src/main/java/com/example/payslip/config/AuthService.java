package com.example.payslip.config;

import com.example.payslip.config.filter.authentication.AdminAuthentication;
import com.example.payslip.config.filter.authentication.EmployeeAuthentication;
import com.example.payslip.data.entity.AdminEntity;
import com.example.payslip.data.entity.EmployeeEntity;
import com.example.payslip.data.repository.AdminRepository;
import com.example.payslip.data.repository.EmployeeRepository;
import com.example.payslip.errors.http.UnauthorizedHttpException;
import com.example.payslip.utilities.Base64Codec;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;
    private final Base64Codec base64Codec;

    public void verifyCredential(String encodedAuth){
        String decodedCredentials = base64Codec.decode(encodedAuth);

        String[] credentialsParts = decodedCredentials.split(":");
        if (credentialsParts.length != 2) {
            throw new UnauthorizedHttpException("The authorization was malformed.");
        }

        String username = credentialsParts[0];
        String password = credentialsParts[1];

        UsernamePasswordAuthenticationToken auth = matchRecord(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    // this is not the right logic but for simplicity,
    // we use or logic here
    // if not employee then check admin table
    private UsernamePasswordAuthenticationToken matchRecord(String username, String password){

        Optional<EmployeeEntity> employeeEntity = employeeRepository
                .findEmployeeBy(username);

        if (employeeEntity.isPresent()) {
            EmployeeEntity employee = employeeEntity.get();
            if (passwordEncoder.matches(password, employee.getPassword())) {
                return new EmployeeAuthentication(
                        new User(employee.getId(), employee.getUsername()), null, null, List.of());
            }
        }

        AdminEntity adminEntity = adminRepository
                .findByUsername(username)
                .orElseThrow(() -> new UnauthorizedHttpException("User %s is unauthorized.".formatted(username)));

        if (passwordEncoder.matches(password, adminEntity.getPassword())) {
            return new AdminAuthentication(new User(adminEntity.getId(), adminEntity.getUsername()), null, null, List.of());
        }

        throw new UnauthorizedHttpException("Unable to authenticate.");
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
