package com.paylink.user.application.service;

import org.springframework.stereotype.Service;

import com.paylink.user.application.dto.AddBalanceDTO;
import com.paylink.user.application.port.in.AddBalanceUseCase;
import com.paylink.user.application.port.out.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddBalanceService implements AddBalanceUseCase {
	
	private final UserProfileRepository upr;
	
	@Override
	public void addBalance(AddBalanceDTO balance, Long id) {
		upr.addBalance(balance.getBalance(), id);
	}

}
