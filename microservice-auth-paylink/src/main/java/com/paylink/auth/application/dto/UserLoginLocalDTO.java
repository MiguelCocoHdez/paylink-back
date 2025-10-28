package com.paylink.auth.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor

public class UserLoginLocalDTO {

	private String emailOrUsername;
	private String password;
}
