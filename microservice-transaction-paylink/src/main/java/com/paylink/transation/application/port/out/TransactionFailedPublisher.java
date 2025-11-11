package com.paylink.transation.application.port.out;

import com.paylink.kafka.events.TransactionFailedEvent;

public interface TransactionFailedPublisher {

	void publishTransactionFailed(TransactionFailedEvent transactionFailed);
}
