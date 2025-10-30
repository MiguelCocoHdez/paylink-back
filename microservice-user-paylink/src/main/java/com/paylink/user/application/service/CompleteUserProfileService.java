package com.paylink.user.application.service;

import org.springframework.stereotype.Service;

import com.paylink.user.application.dto.CompleteUserProfileDTO;
import com.paylink.user.application.port.in.CompleteUserProfileUseCase;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompleteUserProfileService implements CompleteUserProfileUseCase {

	private final UserProfileRepository upr;
	
	@Override
	public void completeUserProfile(Long id, CompleteUserProfileDTO completeUser) {
		UserProfile userIncomplete = upr.findById(id);
		
		if(userIncomplete == null) {
			throw new IllegalArgumentException("Usuario no encontrado");
		}
		
		userIncomplete.completeProfile(completeUser.getFullName(), completeUser.getCurrency());
		
		upr.completeUserProfile(userIncomplete);
	}

}
