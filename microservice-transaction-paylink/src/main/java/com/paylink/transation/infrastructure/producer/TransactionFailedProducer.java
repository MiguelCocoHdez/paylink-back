package com.paylink.transation.infrastructure.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.paylink.kafka.events.TransactionFailedEvent;
import com.paylink.transation.application.port.out.TransactionFailedPublisher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionFailedProducer implements TransactionFailedPublisher {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	@Override
	public void publishTransactionFailed(TransactionFailedEvent transactionFailed) {
		kafkaTemplate.send("transaction-failed", transactionFailed);
	}

}
