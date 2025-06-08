package com.example.payslip;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;

class PayslipServiceApplicationTests {

	@Test
	void contextLoads() throws NoSuchAlgorithmException {

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		String hash = "$2a$06$SPrtJuddYQa5wuB1SMnWfe7So6c7cQUpUbAXEQfEvAGBju.FVJmEu";
		boolean matches = encoder.matches("secret1", hash);
		System.out.println(matches);


	}

}
