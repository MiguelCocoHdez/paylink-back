package com.paylink.user.application.port.out;

import com.paylink.kafka.events.TransactionSuccessEvent;

public interface TransactionSuccessListener {

	void notifyTransactionSuccess(TransactionSuccessEvent event);
}
