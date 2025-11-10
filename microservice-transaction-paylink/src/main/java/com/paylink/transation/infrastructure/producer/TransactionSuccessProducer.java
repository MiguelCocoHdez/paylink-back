package com.paylink.transation.infrastructure.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.paylink.kafka.events.TransactionSuccessEvent;
import com.paylink.transation.application.port.out.TransactionSuccessPublisher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionSuccessProducer implements TransactionSuccessPublisher {
	
	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	@Override
	public void publishTransactionSuccess(TransactionSuccessEvent transactionSuccess) {
		kafkaTemplate.send("transaction-success", transactionSuccess);
	}
}
