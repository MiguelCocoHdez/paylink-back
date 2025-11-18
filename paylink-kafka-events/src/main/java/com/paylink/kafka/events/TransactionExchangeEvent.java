package com.paylink.kafka.events;

import java.math.BigDecimal;

public record TransactionExchangeEvent(
		Long transactionId,
		Long senderId,
		Long receiverId,
		BigDecimal amount,
		String currency,
		String targetCurrency
		) {}