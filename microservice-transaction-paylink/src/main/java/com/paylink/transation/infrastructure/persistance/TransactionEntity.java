package com.paylink.transation.infrastructure.persistance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.paylink.transation.domain.model.TransactionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "transactions")
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long senderId;
	private Long receiverId;
	private BigDecimal amount;
	private String currency;
	private String targetCurrency;
	
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	
	private LocalDateTime createdAt;
}
