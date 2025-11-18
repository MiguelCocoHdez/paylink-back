package com.paylink.exchange.application.port.in;

import com.paylink.kafka.events.ExchangeSuccessEvent;

public interface SendExchangeUseCase {

	void sendExchange(ExchangeSuccessEvent exchangeSuccess);
}
