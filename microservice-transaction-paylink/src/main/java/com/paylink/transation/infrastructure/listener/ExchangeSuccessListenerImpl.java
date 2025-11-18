package com.paylink.transation.infrastructure.listener;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.paylink.kafka.events.ExchangeSuccessEvent;
import com.paylink.kafka.events.TransactionSuccessEvent;
import com.paylink.transation.application.port.out.ExchangeSuccessListener;
import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.application.port.out.TransactionSuccessPublisher;
import com.paylink.transation.domain.model.Transaction;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeSuccessListenerImpl implements ExchangeSuccessListener {
	
	private final TransactionRepository tr;
	private final TransactionSuccessPublisher transactionPublisher;

	@Override
	@KafkaListener(
			topics = "exchange-success",
			groupId = "transaction-service-group",
			containerFactory = "kafkaListenerContainerFactory"
			)
	public void listenExchangeSuccess(ExchangeSuccessEvent exchangeSuccess) {		
		Transaction transactionExchange = tr.findById(exchangeSuccess.id());
		
		
		tr.markAsAccepted(transactionExchange.getId());
		
		transactionPublisher.publishTransactionSuccess(new TransactionSuccessEvent(
				transactionExchange.getId(),
				transactionExchange.getSenderId(),
				transactionExchange.getReceiverId(),
				transactionExchange.getCurrency(),
				transactionExchange.getTargetCurrency(),
				transactionExchange.getAmount(),
				exchangeSuccess.newAmount(),
				LocalDateTime.now(),
				"SUCCESS"
				));
	}

}
