package com.paylink.auth.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor

@Builder
public class UserLoginLocalDTO {

	private String emailOrUsername;
	private String password;
}
