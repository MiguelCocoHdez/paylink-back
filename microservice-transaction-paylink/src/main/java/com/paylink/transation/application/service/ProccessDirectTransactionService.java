package com.paylink.transation.application.service;

import org.springframework.stereotype.Service;

import com.paylink.transation.application.port.in.ProccessDirectTransactionUseCase;
import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.domain.model.Transaction;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProccessDirectTransactionService implements ProccessDirectTransactionUseCase {

	private final TransactionValidatorService validator;
	private final TransactionRepository tr;
	
	@Override
	public void validateTransaction(Transaction transaction) {
		validator.validate(transaction);
		
		tr.markAsAccepted(transaction.getId());
		//llamar al transaction-success-producer y decir en bdd que ha sido success
	}

}
