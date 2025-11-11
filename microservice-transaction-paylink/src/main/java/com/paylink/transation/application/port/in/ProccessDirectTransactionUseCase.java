package com.paylink.transation.application.port.in;

import com.paylink.transation.domain.model.Transaction;

public interface ProccessDirectTransactionUseCase {

	void validateTransaction(Transaction transaction);
}
