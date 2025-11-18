package com.paylink.kafka.events;

import java.math.BigDecimal;

public record ExchangeSuccessEvent(
		Long id,
		Long senderId,
		Long receiverId,
		String currency,
		String targetCurrency,
		BigDecimal amount,
		BigDecimal newAmount
		) {}
