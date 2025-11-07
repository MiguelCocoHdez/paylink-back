package com.paylink.transation.domain.service;

import com.paylink.transation.domain.model.Transaction;

public interface TransactionValidator {

	void validate(Transaction transaction);
}
