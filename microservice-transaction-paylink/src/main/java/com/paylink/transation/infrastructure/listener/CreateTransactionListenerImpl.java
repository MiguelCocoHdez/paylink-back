package com.paylink.transation.infrastructure.listener;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.paylink.kafka.events.TransactionCreatedEvent;
import com.paylink.transation.application.port.in.ProccessDirectTransactionUseCase;
import com.paylink.transation.application.port.in.SaveTransactionUseCase;
import com.paylink.transation.application.port.out.CreateTransactionListener;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.model.TransactionStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateTransactionListenerImpl implements CreateTransactionListener {

	private final SaveTransactionUseCase saveTransaction;
	private final ProccessDirectTransactionUseCase directTransaction;
	
	@Override
	@KafkaListener(
			topics = "create-transaction",
			groupId = "transaction-service-group",
			containerFactory = "kafkaListenerContainerFactory"
			)
	public void listenCreateTransaction(TransactionCreatedEvent transaction) {
		Transaction completeTransaction = Transaction.createTransaction(transaction.senderId(), 
				transaction.receiverId(), 
				transaction.amount(), 
				transaction.currency(), 
				transaction.targetCurrency(), 
				TransactionStatus.PENDING, 
				LocalDateTime.now());
		
		saveTransaction.saveTransaction(completeTransaction);
		
		if(completeTransaction.getCurrency().equals(completeTransaction.getTargetCurrency())) {
			directTransaction.validateTransaction(completeTransaction);
		}
	}

}
