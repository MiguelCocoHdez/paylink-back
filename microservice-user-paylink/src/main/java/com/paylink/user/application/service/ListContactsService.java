package com.paylink.user.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paylink.user.application.port.in.ListContactsUseCase;
import com.paylink.user.application.port.out.ContactRepository;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.application.response.ContactsResponse;
import com.paylink.user.domain.model.Contact;
import com.paylink.user.domain.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListContactsService implements ListContactsUseCase {
	
	private final ContactRepository cr;
	private final UserProfileRepository upr;
	
	@Override
	public List<ContactsResponse> listContacts(Long userId) {
		List<Contact> contacts = cr.findContacts(userId);
		List<ContactsResponse> fullInfoContacts = new ArrayList<ContactsResponse>();
		
		for(Contact c : contacts) {
			UserProfile userProfile = upr.findById(c.getContactUserId());
			
			fullInfoContacts.add(ContactsResponse.builder()
					.contactId(c.getId())
					.userId(userProfile.getId())
					.username(userProfile.getUsername())
					.email(userProfile.getEmail())
					.fullName(userProfile.getFullName())
					.build()
					);
		}
		
		return fullInfoContacts;
	}

}
