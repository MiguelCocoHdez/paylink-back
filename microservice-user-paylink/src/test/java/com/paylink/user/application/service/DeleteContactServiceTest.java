package com.paylink.user.application.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.user.application.port.out.ContactRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test DeleteContact Service")
public class DeleteContactServiceTest {

	@Mock
	private ContactRepository cr;
	
	@InjectMocks
	private DeleteContactService deleteContactService;
	
	@Test
	@DisplayName("Debe eliminar el contacto del usuario")
	void debeEliminarContacto() {
		deleteContactService.deleteContact(1L, 2L);
		
		verify(cr).deleteContactByUser(1L, 2L);
	}
}
