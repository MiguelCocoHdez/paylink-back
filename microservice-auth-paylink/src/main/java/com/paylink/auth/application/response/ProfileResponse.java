package com.paylink.auth.application.response;

import com.paylink.auth.domain.model.AuthProvider;
import com.paylink.auth.domain.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor

public class ProfileResponse {

	private Long id;
	private String username;
	private String email;
	private Role rol;
	private AuthProvider authProvider;
}
