package com.paylink.user.infrastructure.persistance;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.paylink.user.application.port.out.ContactRepository;
import com.paylink.user.domain.model.Contact;
import com.paylink.user.infrastructure.mapper.ContactMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaContactRepository implements ContactRepository {

	private final SpringJpaContactRepository cr;
	private final ContactMapper mapper;
	
	@Override
	public void saveContact(Contact contact) {
		cr.save(mapper.toEntity(contact));
	}

	@Override
	public List<Contact> findContacts(Long userId) { //evitas hacer un for e ir mapeando uno a uno
		return cr.findByUserId(userId).stream()
				.map(mapper::toDomain)
				.toList();
	}

	@Override
	public Optional<Contact> findContactById(Long id) { //usar optional para evitar null pointer
		return cr.findById(id).map(mapper::toDomain);
	}

	@Override
	public void deleteById(Long id) {
		cr.deleteById(id);
	}

	@Override
	public void deleteContactByUser(Long userId, Long contactUserId) {
		cr.deleteContactByUser(userId, contactUserId);
	}

}
