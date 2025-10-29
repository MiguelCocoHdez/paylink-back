package com.paylink.auth.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paylink.auth.application.dto.UserLoginLocalDTO;
import com.paylink.auth.application.port.in.LoginLocalUseCase;
import com.paylink.auth.application.port.out.UserRepository;
import com.paylink.auth.domain.model.User;
import com.paylink.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginLocalService implements LoginLocalUseCase {
	
	private final UserRepository ur;
	private final JwtService js;
	private final PasswordEncoder pe;

	@Override
	public String loginLocal(UserLoginLocalDTO login) {
		User user;
		
		if(login.getEmailOrUsername().contains("@")) {
			user = ur.findByEmail(login.getEmailOrUsername());
		} else {
			user = ur.findByUsername(login.getEmailOrUsername());
		}
		
		if(user == null) {
			throw new IllegalArgumentException("Usuario no registrado");
		}
		
		if(!pe.matches(login.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Contraseña incorrecta");
		}
		
		return js.generateToken(
				org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword())
				.authorities("USER")
				.build());
	}
}
