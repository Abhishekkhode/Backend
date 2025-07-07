package com.suryapropertyconsultant.suryapropertyconsultant;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration; // Import this
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration; // Import this

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class, DataSourceAutoConfiguration.class}) // <-- Add this line
public class SuryapropertyconsultantApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuryapropertyconsultantApplication.class, args);
	}

	@PostConstruct
	public void logPort() {
		System.out.println(">>> Listening on port: " + System.getenv("PORT"));
	}


}