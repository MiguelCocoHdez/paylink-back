package com.paylink.exchange.application.service;

import org.springframework.stereotype.Service;

import com.paylink.exchange.application.port.in.SendExchangeUseCase;
import com.paylink.exchange.application.port.out.ExchangeSuccessPublisher;
import com.paylink.kafka.events.ExchangeSuccessEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeAndSendService implements SendExchangeUseCase {
	
	private final ExchangeSuccessPublisher exchangeSuccessPublisher;
	
	@Override
	public void sendExchange(ExchangeSuccessEvent exchangeSuccess) {
		exchangeSuccessPublisher.publishExchangeSuccess(exchangeSuccess);
	}
}
