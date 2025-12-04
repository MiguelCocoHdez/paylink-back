package com.paylink.user.application.service;

import org.springframework.stereotype.Service;

import com.paylink.user.application.dto.AddBalanceDTO;
import com.paylink.user.application.port.in.AddBalanceUseCase;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddBalanceService implements AddBalanceUseCase {
	
	private final UserProfileRepository upr;
	
	@Override
	public void addBalance(AddBalanceDTO balance, String email) {
		UserProfile user = upr.findByEmail(email);
		
		user.addBalance(balance.getBalance());
		
		upr.setBalance(user.getBalance(), user.getId()); //Actualiza en bdd el usuario con el nuevo balance
	}

}
