package com.paylink.transation.application.service;

import org.springframework.stereotype.Service;

import com.paylink.transation.application.port.in.SaveTransactionUseCase;
import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.domain.model.Transaction;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveTransactionService implements SaveTransactionUseCase {
	
	private final TransactionRepository tr;
	
	@Override
	public Transaction saveTransaction(Transaction transaction) {
		return tr.save(transaction);
	}
}
