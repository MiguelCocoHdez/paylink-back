package com.paylink.user.application.port.out;

import com.paylink.kafka.events.TransactionCreatedEvent;

public interface CreateTransactionPublisher {

	void createTransaction(TransactionCreatedEvent transaction);
}
