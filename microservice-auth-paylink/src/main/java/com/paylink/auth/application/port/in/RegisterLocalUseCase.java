package com.paylink.auth.application.port.in;

import com.paylink.auth.application.dto.UserRegisterLocalDTO;

public interface RegisterLocalUseCase {

	String registerLocal(UserRegisterLocalDTO user);
}
