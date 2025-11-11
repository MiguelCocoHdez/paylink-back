package com.paylink.user.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;

@Getter

public class UserProfile {

	private Long id;
	private Long authUserId;
	private String username;
	private String email;
	private String fullName;
	private BigDecimal balance;
	private String currency;
	
	
	
	private UserProfile(String fullName, String currency) {
		super();
		this.fullName = fullName;
		this.currency = currency;
	}

	private UserProfile(Long authUserId, String username, String email, BigDecimal balance) {
		super();
		this.authUserId = authUserId;
		this.username = username;
		this.email = email;
		this.balance = balance;
	}
	
	private UserProfile(Long id, Long authUserId, String username, String email, String fullName, BigDecimal balance,
			String currency) {
		super();
		this.id = id;
		this.authUserId = authUserId;
		this.username = username;
		this.email = email;
		this.fullName = fullName;
		this.balance = balance;
		this.currency = currency;
	}
	
	//Factories
	public static UserProfile createFromAuth(Long authUserId, String username, String email) {
		return new UserProfile(
				authUserId,
				username,
				email,
				BigDecimal.valueOf(0));
	}
	
	public static UserProfile fromEntity(Long id, Long authUserId, String username, String email, String fullName, BigDecimal balance, String currency) {
		return new UserProfile(
				id,
				authUserId,
				username,
				email,
				fullName,
				balance,
				currency
				);
	}
	
	//Metodos
	public void completeProfile(String fullName, String currency) {
		this.fullName = fullName;
		this.currency = currency;
	}
	
	public void addBalance(BigDecimal balance) {
		this.balance = this.balance.add(balance).setScale(2, RoundingMode.HALF_UP); //Evita que el balance tenga mas de dos decimales
	}

}
