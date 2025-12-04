package com.paylink.user.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paylink.user.application.dto.AddBalanceDTO;
import com.paylink.user.application.dto.CompleteUserProfileDTO;
import com.paylink.user.application.dto.CreateTransactionDTO;
import com.paylink.user.application.port.in.AddBalanceUseCase;
import com.paylink.user.application.port.in.CompleteUserProfileUseCase;
import com.paylink.user.application.port.in.CreateTransactionUseCase;
import com.paylink.user.application.port.in.GetUserProfileUseCase;
import com.paylink.user.application.response.GetUserProfileResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping("/paylink/user")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final CompleteUserProfileUseCase completeUser;
	private final AddBalanceUseCase addBalance;
	private final CreateTransactionUseCase createTransaction;
	private final GetUserProfileUseCase getUserProfile;
	
	@PostMapping("completeUser/{id}")
	ResponseEntity<String> completeUser(@PathVariable Long id, @RequestBody CompleteUserProfileDTO userComplete) {
		completeUser.completeUserProfile(id, userComplete);
		
		return ResponseEntity.ok("Usuario completado correctamente");
	}
	
	@PostMapping("/addBalance")
	ResponseEntity<String> addBalance(Authentication auth, @RequestBody AddBalanceDTO balance) {
		String email = (String) auth.getPrincipal();
		addBalance.addBalance(balance, email);
		
		return ResponseEntity.ok("Balance añadido correctamente");
	}
	
	@PostMapping("createTransaction/{senderId}")
	ResponseEntity<String> createTransaction(@PathVariable Long senderId, @RequestBody CreateTransactionDTO transactionData) {
		createTransaction.createTransaction(senderId, transactionData);
		
		return ResponseEntity.ok("Realizando transaccion...");
	}
	
	@GetMapping("/userProfile")
	ResponseEntity<GetUserProfileResponse> userProfile(Authentication auth) {
		String email = (String) auth.getPrincipal();
		
		return ResponseEntity.ok(getUserProfile.getUserProfile(email));
	}
}
