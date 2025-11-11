package com.paylink.user.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.paylink.kafka.events.TransactionCreatedEvent;
import com.paylink.user.application.dto.CreateTransactionDTO;
import com.paylink.user.application.port.in.CreateTransactionUseCase;
import com.paylink.user.application.port.out.CreateTransactionPublisher;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateTransactionService implements CreateTransactionUseCase {
	
	private final CreateTransactionPublisher createTransaction;
	private final UserProfileRepository upr;

	@Override
	public void createTransaction(Long senderId, CreateTransactionDTO transactionData) {
		UserProfile sender = upr.findById(senderId);
		
		validateBalance(sender, sender.getBalance());
		
		String currency = sender.getCurrency();
		String targetCurrency = upr.findById(transactionData.getReceiverId()).getCurrency();
		
		createTransaction.createTransaction(new TransactionCreatedEvent(senderId, transactionData.getReceiverId(), transactionData.getAmount(), 
				currency, targetCurrency));
		
		
	}
	
	//validaciones
	private void validateBalance(UserProfile user, BigDecimal balance) {
		if(user.getBalance().compareTo(balance) < 0) {
			throw new IllegalArgumentException("Balance insuficiente para realizar la transacción");
		}
	}
	
}
