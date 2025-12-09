package com.paylink.user.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.paylink.user.domain.model.Contact;
import com.paylink.user.infrastructure.persistance.ContactEntity;

@Component
public class ContactMapper {
    
    public ContactEntity toEntity(Contact contact) {
        return new ContactEntity(
            contact.getId(),
            contact.getUserId(),
            contact.getContactUserId(),
            contact.getAddedAt()
        );
    }
    
    public Contact toDomain(ContactEntity entity) {
        return Contact.fromEntity(
            entity.getId(),
            entity.getUserId(),
            entity.getContactUserId(),
            entity.getAddedAt()
        );
    }
}