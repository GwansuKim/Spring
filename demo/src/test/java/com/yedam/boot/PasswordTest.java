package com.yedam.boot;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

	@Test
	public void passEncrypt() {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String password = bcrypt.encode("1234");
		System.out.println(password);
//		bcrypt.matches(password, password)
	}
}
