package com.paylink.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.user.application.dto.AddBalanceDTO;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test AddBalance Service")
public class AddBalanceServiceTest {

	@Mock
	private UserProfileRepository upr;
	
	@InjectMocks
	private AddBalanceService addBalanceService;
	
	private AddBalanceDTO addBalanceDTO;
	private UserProfile user;
	
	@BeforeEach
	void setUp() {
		addBalanceDTO = AddBalanceDTO.builder()
				.balance(new BigDecimal("50.00"))
				.build();
		
		user = UserProfile.fromEntity(
				1L,
				1L,
				"test",
				"test@gmail.com",
				"testFull",
				new BigDecimal("100.00"),
				"EUR"
		);
	}
	
	@Nested
	@DisplayName("Test Success Add Balance")
	class successAddBalance {
		
		@BeforeEach
		void setUpSuccess() {
			when(upr.findByEmail("test@gmail.com")).thenReturn(user);
		}
		
		@Test
		@DisplayName("Debe buscar el usuario por email")
		void debeBuscarUsuarioPorEmail() {
			addBalanceService.addBalance(addBalanceDTO, "test@gmail.com");
			
			verify(upr).findByEmail("test@gmail.com");
		}
		
		@Test
		@DisplayName("Debe sumar el balance al usuario")
		void debeSumarBalance() {
			addBalanceService.addBalance(addBalanceDTO, "test@gmail.com");
			
			assertEquals(
				new BigDecimal("150.00"),
				user.getBalance()
			);
		}
		
		@Test
		@DisplayName("Debe actualizar el balance en base de datos")
		void debeActualizarBalanceEnBBDD() {
			addBalanceService.addBalance(addBalanceDTO, "test@gmail.com");
			
			verify(upr).setBalance(
				new BigDecimal("150.00"),
				1L
			);
		}
	}
	
	@Nested
	@DisplayName("Test Failed Add Balance")
	class failedAddBalance {
		
		@Test
		@DisplayName("NO debe actualizar balance si el usuario no existe")
		void noDebeActualizarSiUsuarioNoExiste() {
			when(upr.findByEmail("test@gmail.com")).thenReturn(null);
			
			try {
				addBalanceService.addBalance(addBalanceDTO, "test@gmail.com");
			} catch (Exception e) {
				// De momento no hay excepcion creada
			}
			
			verify(upr, never()).setBalance(any(), anyLong());
		}
	}
}
