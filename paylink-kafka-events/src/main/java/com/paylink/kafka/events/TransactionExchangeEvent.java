package com.paylink.kafka.events;

import java.math.BigDecimal;

public record TransactionExchangeEvent(
		Long transactionId,
		BigDecimal amount,
		String currency,
		String targetCurrency
		) {}