package com.paylink.user.application.port.in;

import com.paylink.user.application.dto.CreateTransactionDTO;

public interface CreateTransactionUseCase {

	void createTransaction(Long senderId, CreateTransactionDTO transactionData);
}
