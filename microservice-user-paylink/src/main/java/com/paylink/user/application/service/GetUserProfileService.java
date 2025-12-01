package com.paylink.user.application.service;

import org.springframework.stereotype.Service;

import com.paylink.user.application.port.in.GetUserProfileUseCase;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.application.response.GetUserProfileResponse;
import com.paylink.user.domain.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserProfileService implements GetUserProfileUseCase {
	
	private final UserProfileRepository upr;

	@Override
	public GetUserProfileResponse getUserProfile(String email) {
		UserProfile user = upr.findByEmail(email);
		
		return GetUserProfileResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.email(user.getEmail())
				.fullName(user.getFullName())
				.balance(user.getBalance())
				.currency(user.getCurrency())
				.build();
	}

}
