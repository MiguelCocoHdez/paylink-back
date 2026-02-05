package com.paylink.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor

@Builder
public class CompleteUserProfileDTO {

	private String fullName;
	private String currency;
}
