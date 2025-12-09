package com.paylink.user.application.port.out;

import java.util.List;
import java.util.Optional;

import com.paylink.user.domain.model.Contact;

public interface ContactRepository {

	void saveContact(Contact contact);
	
	List<Contact> findContacts(Long userId);
	
	Optional<Contact> findContactById(Long id);
	
	void deleteById(Long id);
}
