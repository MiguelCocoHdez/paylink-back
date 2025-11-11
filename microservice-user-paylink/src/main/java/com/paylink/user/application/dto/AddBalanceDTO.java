package com.paylink.user.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor

public class AddBalanceDTO {

	private BigDecimal balance;
}
