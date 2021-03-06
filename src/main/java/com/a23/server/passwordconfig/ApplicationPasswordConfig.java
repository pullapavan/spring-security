package com.a23.server.passwordconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * @author pavanpulla
 *
 */
@Configuration
public class ApplicationPasswordConfig {
	
	@Bean	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
