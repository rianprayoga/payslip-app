package com.example.payslip;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

class PayslipServiceApplicationTests {

	@Test
	void contextLoads() throws NoSuchAlgorithmException {

		long millisInADay = 86_400_000L;
		long todayAtZero = 1749427200000L;
		long todayAtCurrent = System.currentTimeMillis();

		Instant instantToday = Instant.ofEpochMilli(todayAtCurrent);
		long startOfToday = instantToday.truncatedTo(ChronoUnit.DAYS).toEpochMilli();
        System.out.printf("midnight from todayAtCurrent  %s%n", startOfToday);

		Instant instantTodayZero = Instant.ofEpochMilli(todayAtZero);
		long startOfTodayZero = instantTodayZero.truncatedTo(ChronoUnit.DAYS).toEpochMilli();
		System.out.printf("midnight from todayAtZero  %s%n", startOfTodayZero);
		System.out.printf("midnight from todayAtZero  %s%n", todayAtZero + millisInADay - 1000);

		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(1749340800000L);
		int i = instance.get(Calendar.DAY_OF_WEEK);
		System.out.println(i);


	}

}
