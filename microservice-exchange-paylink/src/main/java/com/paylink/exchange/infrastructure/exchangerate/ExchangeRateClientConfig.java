package com.paylink.exchange.infrastructure.exchangerate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ExchangeRateClientConfig {

	@Value("${exchangerate.api.base-url}")
    private String baseUrl;
	
	 @Bean
	 RestClient exchangeRateRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .build();
	 }
}
