package com.paylink.user.application.port.in;

import java.util.List;

import com.paylink.user.application.response.SearchContactsResponse;

public interface SearchContactsUseCase {

	List<SearchContactsResponse> searchContacts(String contactInfo);
}
