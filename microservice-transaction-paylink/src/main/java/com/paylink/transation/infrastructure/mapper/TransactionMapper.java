package com.paylink.transation.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.infrastructure.persistance.TransactionEntity;

@Component
public class TransactionMapper {

	public Transaction toDomain(TransactionEntity entity) {
		return Transaction.fromEntity(entity.getId(), 
				entity.getSenderId(), 
				entity.getReceiverId(), 
				entity.getAmount(), 
				entity.getCurrency(), 
				entity.getTargetCurrency(), 
				entity.getStatus(), 
				entity.getCreatedAt());
	}
	
	public TransactionEntity toEntity(Transaction domain) {
		TransactionEntity entity = new TransactionEntity();
		
		entity.setId(domain.getId());
        entity.setSenderId(domain.getSenderId());
        entity.setReceiverId(domain.getReceiverId());
        entity.setAmount(domain.getAmount());
        entity.setCurrency(domain.getCurrency());
        entity.setTargetCurrency(domain.getTargetCurrency());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        
        return entity;
	}
}
