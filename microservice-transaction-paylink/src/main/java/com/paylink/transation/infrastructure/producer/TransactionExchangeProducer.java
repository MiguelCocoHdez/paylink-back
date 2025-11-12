package com.paylink.transation.infrastructure.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.paylink.kafka.events.TransactionExchangeEvent;
import com.paylink.transation.application.port.out.TransactionExchangePublisher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionExchangeProducer implements TransactionExchangePublisher {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	@Override
	public void transactionExchange(TransactionExchangeEvent transactionExchange) {
		kafkaTemplate.send("different-exchange-transaction", transactionExchange);
	}

}
