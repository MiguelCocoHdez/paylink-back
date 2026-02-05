package com.paylink.user.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.user.application.dto.ContactRelationDTO;
import com.paylink.user.application.port.out.ContactRepository;
import com.paylink.user.application.port.out.UserProfileRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test SaveContact Service")
public class SaveContactServiceTest {

	@Mock
	private ContactRepository cr;
	
	@Mock
	private UserProfileRepository upr;
	
	@InjectMocks
	private SaveContactService saveContactService;
	
	private ContactRelationDTO contact;
	
	@BeforeEach
	void setUp() {
		contact = ContactRelationDTO.builder()
				.userId(1L)
				.contactUserId(2L)
				.build();
	}
	
	@Nested
	@DisplayName("Test Success Save Contact")
	class successSaveContact {
		
		@BeforeEach
		void setUpSuccess() {
			when(upr.existsById(2L)).thenReturn(true);
		}
		
		@Test
		@DisplayName("Debe guardar el contacto si el usuario existe")
		void debeGuardarContacto() {
			saveContactService.saveContact(contact);
			
			verify(cr).saveContact(org.mockito.ArgumentMatchers.any());
		}
	}
	
	@Nested
	@DisplayName("Test Failed Save Contact")
	class failedSaveContact {
		
		@BeforeEach
		void setUpFail() {
			when(upr.existsById(2L)).thenReturn(false);
		}
		
		@Test
		@DisplayName("Debe lanzar excepción si el usuario no existe")
		void debeLanzarExcepcionSiNoExiste() {
			assertThrows(IllegalArgumentException.class,
					() -> saveContactService.saveContact(contact));
		}
		
		@Test
		@DisplayName("NO debe llamar a saveContact si el usuario no existe")
		void noDebeGuardarContacto() {
			try {
				saveContactService.saveContact(contact);
			} catch (IllegalArgumentException ignored) {}
			
			verify(cr, never()).saveContact(org.mockito.ArgumentMatchers.any());
		}
	}
}
