package com.paylink.auth.application.port.in;

import com.paylink.auth.application.dto.UserLoginLocalDTO;

public interface LoginLocalUseCase {

	String loginLocal(UserLoginLocalDTO user);
}
