package com.paylink.user.application.port.in;

import java.util.List;

import com.paylink.user.application.response.ContactsResponse;

public interface ListContactsUseCase {

	List<ContactsResponse> listContacts(Long userId);
}
