package com.paylink.auth.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Builder
public class UserRegisterLocalDTO {

	private String username;
	private String email;
	private String password;
}
