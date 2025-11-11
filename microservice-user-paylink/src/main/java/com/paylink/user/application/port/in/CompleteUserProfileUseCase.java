package com.paylink.user.application.port.in;

import com.paylink.user.application.dto.CompleteUserProfileDTO;

public interface CompleteUserProfileUseCase {

	void completeUserProfile(Long id, CompleteUserProfileDTO completeUser);
}
