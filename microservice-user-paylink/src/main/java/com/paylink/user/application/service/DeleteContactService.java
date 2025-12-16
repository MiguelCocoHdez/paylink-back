package com.paylink.user.application.service;

import org.springframework.stereotype.Service;

import com.paylink.user.application.port.in.DeleteContactUseCase;
import com.paylink.user.application.port.out.ContactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteContactService implements DeleteContactUseCase {
	
	private final ContactRepository cr;
	
	@Override
	public void deleteContact(Long userId, Long contactUserId) {
		cr.deleteContactByUser(userId, contactUserId);
	}

}
