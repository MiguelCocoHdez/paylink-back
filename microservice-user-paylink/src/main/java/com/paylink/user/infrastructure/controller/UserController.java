package com.paylink.user.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paylink.user.application.dto.AddBalanceDTO;
import com.paylink.user.application.dto.CompleteUserProfileDTO;
import com.paylink.user.application.port.in.AddBalanceUseCase;
import com.paylink.user.application.port.in.CompleteUserProfileUseCase;

import lombok.RequiredArgsConstructor;

@RequestMapping("/paylink/user")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final CompleteUserProfileUseCase completeUser;
	private final AddBalanceUseCase addBalance;
	
	@PostMapping("completeUser/{id}")
	ResponseEntity<String> completeUser(@PathVariable Long id, @RequestBody CompleteUserProfileDTO userComplete) {
		completeUser.completeUserProfile(id, userComplete);
		
		return ResponseEntity.ok("Usuario completado correctamente");
	}
	
	@PostMapping("addBalance/{id}")
	ResponseEntity<String> addBalance(@PathVariable Long id, @RequestBody AddBalanceDTO balance) {
		addBalance.addBalance(balance, id);
		
		return ResponseEntity.ok("Balance añadido correctamente");
	}
}
