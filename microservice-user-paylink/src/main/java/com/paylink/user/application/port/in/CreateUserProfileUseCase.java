package com.paylink.user.application.port.in;

import com.paylink.kafka.events.UserRegisteredEvent;

public interface CreateUserProfileUseCase {

	void createUserProfile(UserRegisteredEvent event);
}
