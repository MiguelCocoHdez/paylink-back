package com.paylink.transation.infrastructure.persistance;

import org.springframework.stereotype.Repository;

import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.model.TransactionStatus;
import com.paylink.transation.infrastructure.mapper.TransactionMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaTransactionRepository implements TransactionRepository {
	
	private final SpringJpaTransactionRepository tr;
	private final TransactionMapper mapper;
	
	@Override
	public Transaction save(Transaction transaction) {
		TransactionEntity entity = tr.save(mapper.toEntity(transaction));
		
		return mapper.toDomain(entity);
	}

	@Override
	public void markAsFailed(Long id) {
		tr.changeStatus(id, TransactionStatus.FAILED.name());
	}

	@Override
	public void markAsAccepted(Long id) {
		tr.changeStatus(id, TransactionStatus.SUCCESS.name()); 
	}

	@Override
	public void markAsProcessing(Long id) {
		tr.changeStatus(id, TransactionStatus.PROCESSING.name());
	}

	@Override
	public Transaction findById(Long id) {
		return mapper.toDomain(tr.findUserById(id));
	}
}
