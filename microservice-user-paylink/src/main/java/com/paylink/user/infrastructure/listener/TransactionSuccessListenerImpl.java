package com.paylink.user.infrastructure.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.paylink.kafka.events.TransactionSuccessEvent;
import com.paylink.user.application.port.out.TransactionSuccessListener;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionSuccessListenerImpl implements TransactionSuccessListener {

	private final UserProfileRepository upr;
	private final SimpMessagingTemplate webSocket;
	
	@Override
	@KafkaListener(
			topics = "transaction-success",
			groupId = "user-service-group",
			containerFactory = "kafkaListenerContainerFactory"
			)
	public void notifyTransactionSuccess(TransactionSuccessEvent event) {
		UserProfile sender = upr.findById(event.senderId());
		UserProfile receiver = upr.findById(event.receiverId());
		
		if(event.exchangedAmount() == null) {
			upr.setBalance(sender.getBalance().subtract(event.amount()), sender.getId()); //restas el balance al que lo envia
			upr.setBalance(receiver.getBalance().add(event.amount()), receiver.getId()); //Sumas el balance al que recibe
		} else {
			upr.setBalance(sender.getBalance().subtract(event.amount()), sender.getId()); //restas el balance normal enviadi al que lo envia
			upr.setBalance(receiver.getBalance().add(event.exchangedAmount()), receiver.getId()); //Sumas el balance del exchangedAmount al que recibe
		}
		
		webSocket.convertAndSend(
				"/topic/transactions",
				event
				);
	}

}
