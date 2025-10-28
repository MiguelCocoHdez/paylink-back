package com.paylink.user.application.port.out;

import com.paylink.kafka.events.UserRegisteredEvent;

public interface UserRegisteredListener {

	void listenUserRegistered(UserRegisteredEvent event);
}
