package com.paylink.kafka.events;

public record UserRegisteredEvent(
		
		Long authUserId,
		String username,
		String email
) {}