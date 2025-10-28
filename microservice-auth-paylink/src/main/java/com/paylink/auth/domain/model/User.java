package com.paylink.auth.domain.model;

import lombok.Getter;

@Getter

public class User {

	private Long id;
	private String username;
	private String email;
	private String password;
	private Role role;
	private AuthProvider authProvider;
	
	private User(String username, String email, String password, Role role, AuthProvider authProvider) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.authProvider = authProvider;
	}
	
	private User(Long id, String username, String email, String password, Role role, AuthProvider authProvider) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.authProvider = authProvider;
	}
	
	//Factories
	public static User createUserLocal(String username, String email, String password) {
		return new User(username, 
				email,
				password,
				Role.USER,
				AuthProvider.LOCAL);
	}
	
	public static User createUserGoogle(String username, String email) {
		return new User(username,
				email,
				null,
				Role.USER,
				AuthProvider.GOOGLE);
	}
	
	public static User fromEntity(Long id, String username, String email, String password, Role role, AuthProvider authProvider) {
		return new User(id,
				username,
				email,
				password,
				role,
				authProvider);
	}
}
