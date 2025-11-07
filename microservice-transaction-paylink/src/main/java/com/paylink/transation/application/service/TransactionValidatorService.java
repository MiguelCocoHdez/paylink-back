package com.paylink.transation.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.service.TransactionValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionValidatorService implements TransactionValidator {

	private final TransactionRepository tr;
	//importar el kafkaTemplate de transaction-failed
	
	@Override
	public void validate(Transaction transaction) {
		if(transaction.getSenderId() == null || transaction.getReceiverId() == null) {
			tr.markAsFailed(transaction.getId());
			
			throw new IllegalArgumentException("El emisor o receptor no existen");
		}
		
		if (transaction.getSenderId().equals(transaction.getReceiverId())) {
			tr.markAsFailed(transaction.getId());
			
            throw new IllegalArgumentException("No puedes darte dinero a ti mismo");
        }
		
		if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			tr.markAsFailed(transaction.getId());
			
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
	}
}
