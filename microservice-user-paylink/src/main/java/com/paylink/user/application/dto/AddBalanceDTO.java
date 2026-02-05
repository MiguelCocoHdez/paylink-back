package com.paylink.user.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor

@Builder
public class AddBalanceDTO {

	private BigDecimal balance;
}
