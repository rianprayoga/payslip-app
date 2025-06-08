package com.example.payslip;

import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class PayslipServiceApplicationTests {

	@Test
	void contextLoads() throws NoSuchAlgorithmException {
		String a = "what";
		String aHash = "4A2028ECEAC5E1F4D252EA13C71ECEC6";
		String b = "WHAT";

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(a.getBytes());

		String s = DatatypeConverter.printHexBinary(md5.digest());
		assert s.equals(aHash);

		md5.update(b.getBytes());
		s = DatatypeConverter.printHexBinary(md5.digest());
		assert !aHash.equals(s);


	}

}
