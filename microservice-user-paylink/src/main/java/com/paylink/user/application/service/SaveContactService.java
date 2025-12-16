package com.paylink.user.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.paylink.user.application.dto.ContactRelationDTO;
import com.paylink.user.application.port.in.SaveContactUseCase;
import com.paylink.user.application.port.out.ContactRepository;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.Contact;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveContactService implements SaveContactUseCase {
	
	private final ContactRepository cr;
	private final UserProfileRepository upr;
	
	@Override
	public void saveContact(ContactRelationDTO contact) {
		if(!upr.existsById(contact.getContactUserId())) {
			throw new IllegalArgumentException("Crear excepcion personalizada");
		}
		
		cr.saveContact(Contact.createContact(
				contact.getUserId(), 
				contact.getContactUserId(), 
				LocalDateTime.now())
				);
	}

}
