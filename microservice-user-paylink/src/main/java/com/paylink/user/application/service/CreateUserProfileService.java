package com.paylink.user.application.service;

import org.springframework.stereotype.Service;

import com.paylink.kafka.events.UserRegisteredEvent;
import com.paylink.user.application.port.in.CreateUserProfileUseCase;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserProfileService implements CreateUserProfileUseCase {
	
	private final UserProfileRepository upr;
	
	@Override
	public void createUserProfile(UserRegisteredEvent event) {
		UserProfile userProfile = UserProfile.createFromAuth(event.authUserId(), event.username(), event.email());
		
		upr.save(userProfile);
	}

}
