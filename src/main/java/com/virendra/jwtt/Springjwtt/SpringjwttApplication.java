package com.virendra.jwtt.Springjwtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringjwttApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringjwttApplication.class, args);
	}
	
	@Bean //this Bean is for to transfor deta controller token genration
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}


}
