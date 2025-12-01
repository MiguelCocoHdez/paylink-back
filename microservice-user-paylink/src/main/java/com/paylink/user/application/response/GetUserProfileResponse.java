package com.paylink.user.application.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor

@Builder
public class GetUserProfileResponse {

	private Long id;
	private String username;
	private String email;
	private String fullName;
	private BigDecimal balance;
	private String currency;
	
}
