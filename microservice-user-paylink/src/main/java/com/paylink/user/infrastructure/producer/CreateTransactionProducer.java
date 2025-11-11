package com.paylink.user.infrastructure.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.paylink.kafka.events.TransactionCreatedEvent;
import com.paylink.user.application.port.out.CreateTransactionPublisher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateTransactionProducer implements CreateTransactionPublisher {
	
	private final KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplate;

	@Override
	public void createTransaction(TransactionCreatedEvent transaction) {
		kafkaTemplate.send("create-transaction", transaction);
	}

}
