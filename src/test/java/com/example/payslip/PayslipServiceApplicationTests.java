package com.example.payslip;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class PayslipServiceApplicationTests {

	@Test
	void contextLoads() throws NoSuchAlgorithmException {


		double a = 1234;
		BigDecimal salary = BigDecimal.valueOf(a);
		int b = 160;

		System.out.println(salary.divide(BigDecimal.valueOf(b)));
		BigDecimal salaryPerHour = salary.divide(BigDecimal.valueOf(b));
//		int i = 20 * 8 *
//				salaryPerHour.multiply(BigDecimal.valueOf(160L));
//		BigDecimal x = salaryPerHour.multiply(BigDecimal.valueOf(160L));
//		System.out.println(x);

		Set<String> asd = new HashSet<>();
		asd.addAll(List.of("a", "b"));
		asd.addAll(List.of("b", "c"));
		asd.addAll(List.of("d", "e"));
		System.out.println(asd);
	}

}
