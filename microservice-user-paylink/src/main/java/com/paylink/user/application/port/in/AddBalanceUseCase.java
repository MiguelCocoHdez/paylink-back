package com.paylink.user.application.port.in;

import com.paylink.user.application.dto.AddBalanceDTO;

public interface AddBalanceUseCase {

	void addBalance(AddBalanceDTO balance, String email);
}
