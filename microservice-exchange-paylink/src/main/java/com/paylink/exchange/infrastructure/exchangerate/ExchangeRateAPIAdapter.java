package com.paylink.exchange.infrastructure.exchangerate;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.paylink.exchange.application.port.out.ExchangeRateAPI;
import com.paylink.exchange.domain.model.ExchangeRate;
import com.paylink.exchange.domain.model.ExchangeResult;
import com.paylink.exchange.domain.model.ExchangeResultAPI;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeRateAPIAdapter implements ExchangeRateAPI {

	private final RestClient restClient;
	
	@Value("${exchangerate.api.key}")
	private String key;
	
	@Override
	@Cacheable(
			value = "exchangeRates",
			key = "#exchange.fromCurrency + '-' + #exchange.toCurrency"
			)
	public ExchangeResult convert(ExchangeRate exchange) {
		ExchangeResultAPI apiResult = restClient.get()
			.uri(uriBuilder -> uriBuilder
					.path("/"+key+"/pair/{currency}/{targetCurrency}")
					.build(exchange.getFromCurrency(), exchange.getToCurrency())
					)
			.retrieve()
			.body(ExchangeResultAPI.class);
		
		BigDecimal newAmount = exchange.getAmount().multiply(apiResult.getConversion_rate());
		
		return new ExchangeResult(
				apiResult.getResult(),
				apiResult.getBase_code(),
				apiResult.getTarget_code(),
				apiResult.getConversion_rate(),
				newAmount
				);
	}

}
