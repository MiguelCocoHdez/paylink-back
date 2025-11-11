package com.paylink.user.application.port.out;

import com.paylink.kafka.events.TransactionFailedEvent;

public interface TransactionFailedListener {

	void notifyTransactionFailed(TransactionFailedEvent event);
}
