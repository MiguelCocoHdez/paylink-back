package com.paylink.user.infrastructure.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.paylink.kafka.events.TransactionFailedEvent;
import com.paylink.user.application.port.out.TransactionFailedListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionFailedListenerImpl implements TransactionFailedListener {
	
	private final SimpMessagingTemplate webSocket;
	
	@Override
	@KafkaListener(
			topics = "transaction-failed",
			groupId = "user-service-group",
			containerFactory = "kafkaListenerContainerFactory"
			)
	public void notifyTransactionFailed(TransactionFailedEvent event) {
		webSocket.convertAndSend(
				"/topic/transactions",
				event
				);
	}

}
