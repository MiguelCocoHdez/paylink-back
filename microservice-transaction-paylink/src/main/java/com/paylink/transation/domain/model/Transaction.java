package com.paylink.transation.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter

public class Transaction {

	private Long id;
	private Long senderId;
	private Long receiverId;
	private BigDecimal amount;
	private String currency;
	private String targetCurrency;
	private TransactionStatus status;
	private LocalDateTime createdAt;
	
	private Transaction(Long senderId, Long receiverId, BigDecimal amount, String currency, String targetCurrency,
			TransactionStatus status, LocalDateTime createdAt) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.amount = amount;
		this.currency = currency;
		this.targetCurrency = targetCurrency;
		this.status = status;
		this.createdAt = createdAt;
	}

	private Transaction(Long id, Long senderId, Long receiverId, BigDecimal amount, String currency,
			String targetCurrency, TransactionStatus status, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.amount = amount;
		this.currency = currency;
		this.targetCurrency = targetCurrency;
		this.status = status;
		this.createdAt = createdAt;
	}
	
	public static Transaction createTransaction(Long senderId, Long receiverId, BigDecimal amount, String currency, String targetCurrency,
			TransactionStatus status, LocalDateTime createdAt) {
		return new Transaction(senderId,
				receiverId,
				amount,
				currency,
				targetCurrency,
				status,
				createdAt);
	}
	
	public static Transaction fromEntity(Long id, Long senderId, Long receiverId, BigDecimal amount, String currency,
			String targetCurrency, TransactionStatus status, LocalDateTime createdAt) {
		return new Transaction(id,
				senderId,
				receiverId,
				amount,
				currency,
				targetCurrency,
				status,
				createdAt);
	}
}
