package com.paylink.transation.application.port.out;

import com.paylink.kafka.events.TransactionSuccessEvent;

public interface TransactionSuccessPublisher {

	void publishTransactionSuccess(TransactionSuccessEvent transactionSuccess);
}
