package com.paylink.exchange.infrastructure.exchangerate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.paylink.exchange.application.port.out.ExchangeRateAPI;
import com.paylink.exchange.domain.model.ExchangeRate;
import com.paylink.exchange.domain.model.ExchangeResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeRateAPIAdapter implements ExchangeRateAPI {

	private final RestClient restClient;
	
	@Value("${exchangerate.api.key}")
	private String key;
	
	@Override
	public ExchangeResult convert(ExchangeRate exchange) {
		return restClient.get()
			.uri(uriBuilder -> uriBuilder
					.path("/"+key+"/pair/{currency}/{targetCurrency}/{amount}")
					.build(exchange.getFromCurrency(), exchange.getToCurrency(), exchange.getAmount())
					)
			.retrieve()
			.body(ExchangeResult.class);
	}

}
