package com.paylink.user.application.port.in;

import com.paylink.user.application.dto.ContactRelationDTO;

public interface SaveContactUseCase {

	void saveContact(ContactRelationDTO contact);
}
