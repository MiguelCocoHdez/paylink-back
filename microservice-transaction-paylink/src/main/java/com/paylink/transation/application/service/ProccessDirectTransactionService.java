package com.paylink.transation.application.service;

import org.springframework.stereotype.Service;

import com.paylink.kafka.events.TransactionSuccessEvent;
import com.paylink.transation.application.port.in.ProccessDirectTransactionUseCase;
import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.application.port.out.TransactionSuccessPublisher;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.service.TransactionValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProccessDirectTransactionService implements ProccessDirectTransactionUseCase {

	private final TransactionValidator validator;
	private final TransactionRepository tr;
	private final TransactionSuccessPublisher transactionSuccess;
	
	@Override
	public void validateTransaction(Transaction transaction) {
		if(!validator.validate(transaction)) {
			return;
		}
		
		
		tr.markAsAccepted(transaction.getId());
		
		transactionSuccess.publishTransactionSuccess(new TransactionSuccessEvent(transaction.getId(),
				transaction.getSenderId(),
				transaction.getReceiverId(),
				transaction.getAmount(),
				null,
				transaction.getCreatedAt(),
				"SUCCESS"
				));
	}

}
