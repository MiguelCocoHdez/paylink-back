package com.paylink.transation.application.port.in;

import com.paylink.transation.domain.model.Transaction;

public interface ProcessCurrencyExchangeTransactionUseCase {

	void validateAndSendToExchange(Transaction transaction);
}
