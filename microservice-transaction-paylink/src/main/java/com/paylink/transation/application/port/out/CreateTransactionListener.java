package com.paylink.transation.application.port.out;

import com.paylink.kafka.events.TransactionCreatedEvent;

public interface CreateTransactionListener {

	void listenCreateTransaction(TransactionCreatedEvent transaction);
}
