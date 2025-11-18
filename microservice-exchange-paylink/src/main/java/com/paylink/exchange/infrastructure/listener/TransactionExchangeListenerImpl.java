package com.paylink.exchange.infrastructure.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.paylink.exchange.application.port.in.SendExchangeUseCase;
import com.paylink.exchange.application.port.out.ExchangeRateAPI;
import com.paylink.exchange.application.port.out.TransactionExchangeListener;
import com.paylink.exchange.domain.model.ExchangeRate;
import com.paylink.exchange.domain.model.ExchangeResult;
import com.paylink.kafka.events.ExchangeSuccessEvent;
import com.paylink.kafka.events.TransactionExchangeEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionExchangeListenerImpl implements TransactionExchangeListener {
	
	private final ExchangeRateAPI exchangeRateApi;	
	private final SendExchangeUseCase sendExchange;

	@Override
	@KafkaListener(
			topics = "different-exchange-transaction",
			groupId = "exchange-service-group",
			containerFactory = "kafkaListenerContainerFactory"
			)
	public void listenTransactionExchangeEvent(TransactionExchangeEvent exchangeEvent) {
		ExchangeRate exchange = ExchangeRate.createExchange(
				exchangeEvent.transactionId(),
				exchangeEvent.currency(), 
				exchangeEvent.targetCurrency(), 
				exchangeEvent.amount(),
				null);
		
		ExchangeResult result = exchangeRateApi.convert(exchange);
		exchange.setNewAmount(result.getConversion_result());
		
		sendExchange.sendExchange(new ExchangeSuccessEvent(
				exchange.getTransactionId(),
				exchangeEvent.senderId(),
				exchangeEvent.receiverId(),
				result.getBase_code(),
				result.getTarget_code(),
				exchange.getAmount(),
				result.getConversion_result()
				));
	}

}
