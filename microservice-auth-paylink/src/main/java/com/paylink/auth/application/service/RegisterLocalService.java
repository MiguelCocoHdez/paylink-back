package com.paylink.auth.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paylink.auth.application.dto.UserRegisterLocalDTO;
import com.paylink.auth.application.port.in.RegisterLocalUseCase;
import com.paylink.auth.application.port.out.UserRegisteredPublisher;
import com.paylink.auth.application.port.out.UserRepository;
import com.paylink.auth.domain.model.User;
import com.paylink.auth.infrastructure.security.JwtService;
import com.paylink.kafka.events.UserRegisteredEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterLocalService implements RegisterLocalUseCase {
	
	private final UserRepository ur;
	private final JwtService js;
	private final PasswordEncoder pe;
	private final UserRegisteredPublisher urp;

	@Override
	public String registerLocal(UserRegisterLocalDTO user) {
		String encriptedPw = pe.encode(user.getPassword());
		
		if(ur.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email ya registrado");
		}
		
		if(ur.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Username ya registrado");
		}
		
		User userRegistered = ur.save(User.createUserLocal(user.getUsername(), user.getEmail(), encriptedPw));
		urp.publishUserRegistered(new UserRegisteredEvent(userRegistered.getId(), userRegistered.getUsername(), userRegistered.getEmail()));
		
		return js.generateToken(
				org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(encriptedPw)
				.authorities("USER")
				.build());
	}

}
