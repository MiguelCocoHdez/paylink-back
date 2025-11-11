package com.paylink.transation.domain.service;

import com.paylink.transation.domain.model.Transaction;

public interface TransactionValidator {

	boolean validate(Transaction transaction);
}
