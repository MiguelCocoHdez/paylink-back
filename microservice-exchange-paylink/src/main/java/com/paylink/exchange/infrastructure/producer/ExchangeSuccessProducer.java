package com.paylink.exchange.infrastructure.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.paylink.exchange.application.port.out.ExchangeSuccessPublisher;
import com.paylink.kafka.events.ExchangeSuccessEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeSuccessProducer implements ExchangeSuccessPublisher {
	
	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	@Override
	public void publishExchangeSuccess(ExchangeSuccessEvent exchangeSuccess) {
		kafkaTemplate.send("exchange-success", exchangeSuccess);
	}

}
