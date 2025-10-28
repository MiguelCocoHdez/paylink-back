package com.paylink.auth.application.port.in;

import com.paylink.auth.application.response.ProfileResponse;

public interface ProfileUseCase {

	ProfileResponse getProfile(String email);
}
