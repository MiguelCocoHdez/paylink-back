package com.paylink.transation.application.port.in;

import com.paylink.transation.domain.model.Transaction;

public interface SaveTransactionUseCase {

	Transaction saveTransaction(Transaction transaction);
}
