package com.paylink.auth.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paylink.auth.application.dto.UserLoginLocalDTO;
import com.paylink.auth.application.dto.UserRegisterLocalDTO;
import com.paylink.auth.application.port.in.LoginLocalUseCase;
import com.paylink.auth.application.port.in.ProfileUseCase;
import com.paylink.auth.application.port.in.RegisterLocalUseCase;
import com.paylink.auth.application.response.LoginRegisterResponse;
import com.paylink.auth.application.response.ProfileResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/paylink/auth")
@RequiredArgsConstructor
public class AuthController {

	private final RegisterLocalUseCase registerLocal;
	private final LoginLocalUseCase loginLocal;
	private final ProfileUseCase profile;
	
	@PostMapping("/register")
	ResponseEntity<LoginRegisterResponse> registerLocal(@RequestBody UserRegisterLocalDTO user) {
		String jwt = registerLocal.registerLocal(user);
		
		return ResponseEntity.ok(new LoginRegisterResponse(jwt));
	}
	
	@PostMapping("/login")
	ResponseEntity<LoginRegisterResponse> loginLocal(@RequestBody UserLoginLocalDTO user) {
		String jwt = loginLocal.loginLocal(user);
		
		return ResponseEntity.ok(new LoginRegisterResponse(jwt));
	}
	
	@GetMapping("/profile")
	ResponseEntity<ProfileResponse> profile(Authentication auth) {
		String email = (String) auth.getPrincipal();
		
		return ResponseEntity.ok(profile.getProfile(email));
	}
}
