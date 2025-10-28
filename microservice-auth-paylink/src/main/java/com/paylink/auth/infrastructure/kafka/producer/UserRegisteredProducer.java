package com.paylink.auth.infrastructure.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.paylink.auth.application.port.out.UserRegisteredPublisher;
import com.paylink.kafka.events.UserRegisteredEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRegisteredProducer implements UserRegisteredPublisher  {
	
	private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

	@Override
	public void publishUserRegistered(UserRegisteredEvent event) {
		kafkaTemplate.send("user-registered", event);
	}

}
