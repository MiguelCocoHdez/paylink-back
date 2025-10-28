package com.paylink.auth.application.port.out;

import com.paylink.kafka.events.UserRegisteredEvent;

public interface UserRegisteredPublisher {

	void publishUserRegistered(UserRegisteredEvent event);
}
