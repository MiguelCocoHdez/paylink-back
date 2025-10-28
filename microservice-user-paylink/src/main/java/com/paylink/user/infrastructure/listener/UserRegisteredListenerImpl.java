package com.paylink.user.infrastructure.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.paylink.kafka.events.UserRegisteredEvent;
import com.paylink.user.application.port.in.CreateUserProfileUseCase;
import com.paylink.user.application.port.out.UserRegisteredListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRegisteredListenerImpl implements UserRegisteredListener {
	
	private final CreateUserProfileUseCase createUserProfileUseCase;
	
	@Override
	@KafkaListener(
			topics = "user-registered",
			groupId = "user-service-group",
			containerFactory = "kafkaListenerContainerFactory"
			)
	public void listenUserRegistered(UserRegisteredEvent event) {
		createUserProfileUseCase.createUserProfile(event);
	}

}