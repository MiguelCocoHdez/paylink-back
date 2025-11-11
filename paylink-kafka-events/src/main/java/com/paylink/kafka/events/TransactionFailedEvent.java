package com.paylink.kafka.events;

import java.time.LocalDateTime;

public record TransactionFailedEvent(
		Long transactionId,
		Long senderId,
		Long receiverId,
		String reason,
		LocalDateTime timestamp,
		String status
		) {}