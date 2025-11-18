package com.paylink.kafka.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionSuccessEvent(
		Long transactionId,
		Long senderId,
		Long receiverId,
		String currency,
		String targetCurrency,
		BigDecimal amount,
		BigDecimal exchangedAmount,
		LocalDateTime timestamp,
		String status
		) {}