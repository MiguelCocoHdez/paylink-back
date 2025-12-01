package com.paylink.user.application.port.in;

import com.paylink.user.application.response.GetUserProfileResponse;

public interface GetUserProfileUseCase {

	GetUserProfileResponse getUserProfile(String email);
}
