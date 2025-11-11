package com.paylink.transation.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.paylink.kafka.events.TransactionFailedEvent;
import com.paylink.transation.application.port.out.TransactionFailedPublisher;
import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.service.TransactionValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionValidatorService implements TransactionValidator {

	private final TransactionRepository tr;
	private final TransactionFailedPublisher transactionFailed;
	
	@Override
	public boolean validate(Transaction transaction) {
		if(transaction.getSenderId() == null || transaction.getReceiverId() == null) {
			tr.markAsFailed(transaction.getId());
			transactionFailed.publishTransactionFailed(createFailEvent(transaction, "El emisor o receptor no existen."));
			
			return false;
		}
		
		if (transaction.getSenderId().equals(transaction.getReceiverId())) {
			tr.markAsFailed(transaction.getId());
			transactionFailed.publishTransactionFailed(createFailEvent(transaction, "No puedes darte dinero a ti mismo"));
			
            return false;
        }
		
		if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			tr.markAsFailed(transaction.getId());
			transactionFailed.publishTransactionFailed(createFailEvent(transaction, "La cantidad debe ser mayor a 0"));
			
            return false;
        }
		
		return true;
	}
	
	private TransactionFailedEvent createFailEvent(Transaction transaction, String mensaje) {
		return new TransactionFailedEvent(
				transaction.getId(),
				transaction.getSenderId(),
				transaction.getReceiverId(),
				mensaje,
				LocalDateTime.now(),
				"FAILED"
				);
	}
}
