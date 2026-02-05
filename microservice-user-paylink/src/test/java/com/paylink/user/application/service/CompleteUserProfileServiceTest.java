package com.paylink.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.user.application.dto.CompleteUserProfileDTO;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test CompleteUserProfile Service")
public class CompleteUserProfileServiceTest {

	@Mock
	private UserProfileRepository upr;
	
	@InjectMocks
	private CompleteUserProfileService completeUserProfileService;
	
	private CompleteUserProfileDTO completeUserProfileDTO;
	private UserProfile userIncomplete;
	
	@BeforeEach
	void setUp() {
		completeUserProfileDTO = CompleteUserProfileDTO.builder()
				.fullName("Miguel Ángel")
				.currency("EUR")
				.build();
		
		userIncomplete = UserProfile.fromEntity(
				1L,
				1L,
				"test@gmail.com",
				null,
				null,
				null,
				null
		);
	}
	
	@Nested
	@DisplayName("Test Success Complete User Profile")
	class successCompleteUserProfile {
		
		@BeforeEach
		void setUpSuccess() {
			when(upr.findById(1L)).thenReturn(userIncomplete);
		}
		
		@Test
		@DisplayName("Debe buscar el usuario por id")
		void debeBuscarUsuarioPorId() {
			completeUserProfileService.completeUserProfile(1L, completeUserProfileDTO);
			
			verify(upr).findById(1L);
		}
		
		@Test
		@DisplayName("Debe completar los datos del usuario")
		void debeCompletarDatosUsuario() {
			completeUserProfileService.completeUserProfile(1L, completeUserProfileDTO);
			
			assertEquals("Miguel Ángel", userIncomplete.getFullName());
			assertEquals("EUR", userIncomplete.getCurrency());
		}
		
		@Test
		@DisplayName("Debe persistir el usuario completo")
		void debePersistirUsuario() {
			completeUserProfileService.completeUserProfile(1L, completeUserProfileDTO);
			
			verify(upr).completeUserProfile(userIncomplete);
		}
	}
	
	@Nested
	@DisplayName("Test Failed Complete User Profile")
	class failedCompleteUserProfile {
		
		@Test
		@DisplayName("Debe lanzar excepción si el usuario no existe")
		void debeLanzarExcepcionSiUsuarioNoExiste() {
			when(upr.findById(1L)).thenReturn(null);
			
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> completeUserProfileService.completeUserProfile(1L, completeUserProfileDTO)
			);
			
			assertEquals("Usuario no encontrado", exception.getMessage());
		}
		
		@Test
		@DisplayName("NO debe completar perfil si el usuario no existe")
		void noDebeCompletarPerfil() {
			when(upr.findById(1L)).thenReturn(null);
			
			assertThrows(
				IllegalArgumentException.class,
				() -> completeUserProfileService.completeUserProfile(1L, completeUserProfileDTO)
			);
			
			verify(upr, never()).completeUserProfile(any());
		}
	}
}
