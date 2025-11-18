package com.paylink.transation.application.port.out;

import com.paylink.transation.domain.model.Transaction;

public interface TransactionRepository {

	Transaction save(Transaction transaction);
	
	void markAsFailed(Long id);
	
	void markAsAccepted(Long id);
	
	void markAsProcessing(Long id);
	
	Transaction findById(Long id);
}
