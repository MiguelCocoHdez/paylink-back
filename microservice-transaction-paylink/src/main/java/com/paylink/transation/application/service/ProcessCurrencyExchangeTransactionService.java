package com.paylink.transation.application.service;

import org.springframework.stereotype.Service;

import com.paylink.kafka.events.TransactionExchangeEvent;
import com.paylink.transation.application.port.in.ProcessCurrencyExchangeTransactionUseCase;
import com.paylink.transation.application.port.out.TransactionExchangePublisher;
import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.service.TransactionValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessCurrencyExchangeTransactionService implements ProcessCurrencyExchangeTransactionUseCase {

	private final TransactionValidator validator;
	private final TransactionRepository tr;	
	private final TransactionExchangePublisher transactionExchangePublisher;
	
	@Override
	public void validateAndSendToExchange(Transaction transaction) {
		if(!validator.validate(transaction)) {
			return;
		}
		
		tr.markAsProcessing(transaction.getId());
		
		transactionExchangePublisher.transactionExchange(new TransactionExchangeEvent(
				transaction.getId(),
				transaction.getAmount(),
				transaction.getCurrency(),
				transaction.getTargetCurrency()
				));
	}

}
