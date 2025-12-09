package com.paylink.user.application.port.in;

import com.paylink.user.application.dto.SaveContactDTO;

public interface SaveContactUseCase {

	void saveContact(SaveContactDTO contact);
}
