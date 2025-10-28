package com.paylink.auth.application.service;

import org.springframework.stereotype.Service;

import com.paylink.auth.application.port.in.ProfileUseCase;
import com.paylink.auth.application.port.out.UserRepository;
import com.paylink.auth.application.response.ProfileResponse;
import com.paylink.auth.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService implements ProfileUseCase {
	
	private final UserRepository ur;

	@Override
	public ProfileResponse getProfile(String email) {
		User user = ur.findByEmail(email);
		
		return new ProfileResponse(user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getRole(),
				user.getAuthProvider());
	}

}
