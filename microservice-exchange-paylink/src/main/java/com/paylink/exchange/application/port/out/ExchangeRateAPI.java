package com.paylink.exchange.application.port.out;

import com.paylink.exchange.domain.model.ExchangeRate;
import com.paylink.exchange.domain.model.ExchangeResult;

public interface ExchangeRateAPI {

	ExchangeResult convert(ExchangeRate exchange);
}
