package com.paylink.exchange.application.port.out;

import com.paylink.kafka.events.ExchangeSuccessEvent;

public interface ExchangeSuccessPublisher {

	void publishExchangeSuccess(ExchangeSuccessEvent exchangeSuccess);
}
