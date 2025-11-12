package com.paylink.transation.application.port.out;

import com.paylink.kafka.events.TransactionExchangeEvent;

public interface TransactionExchangePublisher {

	void transactionExchange(TransactionExchangeEvent transactionExchange);
}
