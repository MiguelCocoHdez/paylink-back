package com.paylink.transation.application.port.out;

import com.paylink.kafka.events.ExchangeSuccessEvent;

public interface ExchangeSuccessListener {

	void listenExchangeSuccess(ExchangeSuccessEvent exchangeSuccess);
}
