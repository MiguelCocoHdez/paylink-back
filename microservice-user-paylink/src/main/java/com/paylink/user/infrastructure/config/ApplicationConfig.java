package com.paylink.user.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paylink.security.jwt.JwtAuthFilter;
import com.paylink.security.jwt.JwtService;

@Configuration
public class ApplicationConfig {

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Bean
	JwtService jwtService() {
		return new JwtService(jwtSecret);
	}

	@Bean
    JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService());
    }
}
