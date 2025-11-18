package com.paylink.exchange.domain.model;

import java.math.BigDecimal;

import lombok.Getter;

@Getter

public class ExchangeRate {

	private Long id;
	private Long transactionId;
	private String fromCurrency;
	private String toCurrency;
	BigDecimal amount;
	BigDecimal newAmount;
	
	private ExchangeRate(Long transactionId, String fromCurrency, String toCurrency, BigDecimal amount, BigDecimal newAmount) {
		super();
		this.transactionId = transactionId;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.amount = amount;
		this.newAmount = newAmount;
	}
	
	//Factories
	public static ExchangeRate createExchange(Long transactionId, String fromCurrency, String toCurrency, BigDecimal amount, BigDecimal newAmount) {
		return new ExchangeRate(
				transactionId,
				fromCurrency,
				toCurrency,
				amount,
				newAmount);
	}
	
	//Metodos
	public void setNewAmount(BigDecimal newAmount) {
		this.newAmount = newAmount;
	}
}
