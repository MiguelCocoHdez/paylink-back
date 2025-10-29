package com.paylink.auth.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.paylink.security.jwt.JwtAuthFilter;
import com.paylink.security.jwt.JwtService;

@Configuration
public class ApplicationConfig {
	
	@Value("${jwt.secret}")
    private String jwtSecret;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Bean para tener el jwtSecret personalizado en la configuracion del servicio
	@Bean
	JwtService jwtService() {
		return new JwtService(jwtSecret);
	}
	
	//Bean para inyectar el jwtService con el jwtSecret personalizado en el filtro
	@Bean
    JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService());
    }
}
