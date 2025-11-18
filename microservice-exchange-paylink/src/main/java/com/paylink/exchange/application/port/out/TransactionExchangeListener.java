package com.paylink.exchange.application.port.out;

import com.paylink.kafka.events.TransactionExchangeEvent;

public interface TransactionExchangeListener {

	void listenTransactionExchangeEvent(TransactionExchangeEvent exchangeEvent);
}
