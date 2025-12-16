package com.paylink.user.infrastructure.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paylink.user.application.dto.ContactRelationDTO;
import com.paylink.user.application.port.in.DeleteContactUseCase;
import com.paylink.user.application.port.in.GetUserProfileUseCase;
import com.paylink.user.application.port.in.ListContactsUseCase;
import com.paylink.user.application.port.in.SaveContactUseCase;
import com.paylink.user.application.port.in.SearchContactsUseCase;
import com.paylink.user.application.response.ContactsResponse;
import com.paylink.user.application.response.SearchContactsResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping("/paylink/contact")
@RestController
@RequiredArgsConstructor
public class ContactController {

	private final SaveContactUseCase saveContact;
	private final ListContactsUseCase listContacts;
	private final GetUserProfileUseCase getUserProfile;
	private final SearchContactsUseCase searchContacts;
	private final DeleteContactUseCase deleteContact;
	
	@PostMapping("/save")
	ResponseEntity<String> addContact(@RequestBody ContactRelationDTO contact) {
		saveContact.saveContact(contact);
		
		return ResponseEntity.ok("Contacto añadido correctamente");
	}
	
	@GetMapping("/allContacts")
	ResponseEntity<List<ContactsResponse>> getAllContacts(Authentication auth) {
		String email = (String) auth.getPrincipal();
		Long userId = getUserProfile.getUserProfile(email).getId();
		
		return ResponseEntity.ok(listContacts.listContacts(userId));
	}
	
	@GetMapping("/searchContacts")
	ResponseEntity<List<SearchContactsResponse>> searchContacts(@RequestParam String contactInfo) {
		return ResponseEntity.ok(searchContacts.searchContacts(contactInfo));
	}
	
	@DeleteMapping("/delete")
	ResponseEntity<String> deleteContact(@RequestParam Long userId, @RequestParam Long contactUserId) {
		deleteContact.deleteContact(userId, contactUserId);
		
		return ResponseEntity.ok("Contacto eliminado correctamente");
	}
}
