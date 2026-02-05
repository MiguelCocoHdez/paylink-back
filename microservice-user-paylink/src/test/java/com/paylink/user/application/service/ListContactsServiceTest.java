package com.paylink.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.user.application.port.out.ContactRepository;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.application.response.ContactsResponse;
import com.paylink.user.domain.model.Contact;
import com.paylink.user.domain.model.UserProfile;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test ListContacts Service")
public class ListContactsServiceTest {

	@Mock
	private ContactRepository cr;
	
	@Mock
	private UserProfileRepository upr;
	
	@InjectMocks
	private ListContactsService listContactsService;
	
	private Contact contact1;
	private Contact contact2;
	private UserProfile userProfile1;
	private UserProfile userProfile2;
	
	@BeforeEach
	void setUp() {
		contact1 = Contact.fromEntity(1L,2L, 3L, LocalDateTime.now());
		contact2 = Contact.fromEntity(2L, 10L, 5L, LocalDateTime.now());
		
		userProfile1 = UserProfile.fromEntity(
				2L,
				20L,
				"user1",
				"user1@gmail.com",
				"User One",
				new BigDecimal("100.00"),
				"EUR"
		);
		
		userProfile2 = UserProfile.fromEntity(
				3L,
				30L,
				"user2",
				"user2@gmail.com",
				"User Two",
				new BigDecimal("200.00"),
				"USD"
		);
	}
	
	@Nested
	@DisplayName("Test Success List Contacts")
	class successListContacts {
		
		@BeforeEach
		void setUpSuccess() {
			when(cr.findContacts(10L)).thenReturn(List.of(contact1, contact2));
			when(upr.findById(3L)).thenReturn(userProfile1);
			when(upr.findById(5L)).thenReturn(userProfile2);
		}
		
		@Test
		@DisplayName("Debe buscar los contactos del usuario")
		void debeBuscarContactos() {
			listContactsService.listContacts(10L);
			
			verify(cr).findContacts(10L);
		}
		
		@Test
		@DisplayName("Debe buscar el perfil de cada contacto")
		void debeBuscarPerfilContacto() {
			listContactsService.listContacts(10L);
			
			verify(upr).findById(3L);
			verify(upr).findById(5L);
		}
		
		@Test
		@DisplayName("Debe devolver la lista de contactos con información completa")
		void debeDevolverListaCompleta() {
			List<ContactsResponse> response = listContactsService.listContacts(10L);
			
			assertEquals(2, response.size());
			
			ContactsResponse first = response.get(0);
			assertEquals(1L, first.getContactId());
			assertEquals(2L, first.getUserId());
			assertEquals("user1", first.getUsername());
			assertEquals("user1@gmail.com", first.getEmail());
			assertEquals("User One", first.getFullName());
			
			ContactsResponse second = response.get(1);
			assertEquals(2L, second.getContactId());
			assertEquals(3L, second.getUserId());
			assertEquals("user2", second.getUsername());
			assertEquals("user2@gmail.com", second.getEmail());
			assertEquals("User Two", second.getFullName());
		}
	}
}
