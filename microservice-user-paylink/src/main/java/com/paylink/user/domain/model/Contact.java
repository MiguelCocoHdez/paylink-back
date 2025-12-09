package com.paylink.user.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter

public class Contact {

	private Long id;
	private Long userId; //Id del usuario de la lista de contactos
	private Long contactUserId;
	private LocalDateTime addedAt;
	
	private Contact(Long id, Long userId, Long contactUserId, LocalDateTime addedAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.contactUserId = contactUserId;
		this.addedAt = addedAt;
	}
	
	private Contact(Long userId, Long contactUserId, LocalDateTime addedAt) {
		super();
		this.userId = userId;
		this.contactUserId = contactUserId;
		this.addedAt = addedAt;
	}
	
	//Factories
	public static Contact createContact(Long userId, Long contactUserId, LocalDateTime addedAt) {
		if(userId.equals(contactUserId)) {
            throw new IllegalArgumentException("No puedes añadirte a ti mismo como contacto"); //hacer excepcion
        }
		
		return new Contact(
				userId,
				contactUserId,
				addedAt
				);
	}
	
    public static Contact fromEntity(Long id, Long userId, Long contactUserId, LocalDateTime addedAt) {
        return new Contact(id, userId, contactUserId, addedAt);
    }

}
