package com.paylink.auth.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRegisterLocalDTO {

	private String username;
	private String email;
	private String password;
}
