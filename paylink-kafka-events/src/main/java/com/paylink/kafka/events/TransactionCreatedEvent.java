package com.paylink.kafka.events;

import java.math.BigDecimal;

public record TransactionCreatedEvent(

	Long senderId,
	Long receiverId,
	BigDecimal amount,
	String currency,
	String targetCurrency
) {}