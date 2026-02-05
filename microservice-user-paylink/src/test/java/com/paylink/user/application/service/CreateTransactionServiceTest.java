package com.paylink.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.kafka.events.TransactionCreatedEvent;
import com.paylink.user.application.dto.CreateTransactionDTO;
import com.paylink.user.application.port.out.CreateTransactionPublisher;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test CreateTransaction Service")
public class CreateTransactionServiceTest {

	@Mock
	private CreateTransactionPublisher createTransaction;
	
	@Mock
	private UserProfileRepository upr;
	
	@InjectMocks
	private CreateTransactionService createTransactionService;
	
	private CreateTransactionDTO createTransactionDTO;
	private UserProfile sender;
	private UserProfile receiver;
	
	@BeforeEach
	void setUp() {
		createTransactionDTO = CreateTransactionDTO.builder()
				.receiverId(2L)
				.amount(new BigDecimal("50.00"))
				.build();
		
		sender = UserProfile.fromEntity(
				1L,
				10L,
				"sender",
				"sender@gmail.com",
				"Sender User",
				new BigDecimal("100.00"),
				"EUR"
		);
		
		receiver = UserProfile.fromEntity(
				2L,
				20L,
				"receiver",
				"receiver@gmail.com",
				"Receiver User",
				new BigDecimal("200.00"),
				"USD"
		);
	}
	
	@Nested
	@DisplayName("Test Success Create Transaction")
	class successCreateTransaction {
		
		@BeforeEach
		void setUpSuccess() {
			when(upr.findById(1L)).thenReturn(sender);
			when(upr.findById(2L)).thenReturn(receiver);
		}
		
		@Test
		@DisplayName("Debe buscar el usuario emisor por id")
		void debeBuscarSender() {
			createTransactionService.createTransaction(1L, createTransactionDTO);
			
			verify(upr).findById(1L);
		}
		
		@Test
		@DisplayName("Debe buscar el usuario receptor por id")
		void debeBuscarReceiver() {
			createTransactionService.createTransaction(1L, createTransactionDTO);
			
			verify(upr).findById(2L);
		}
		
		@Test
		@DisplayName("Debe publicar el evento de creación de transacción")
		void debePublicarEvento() {
			createTransactionService.createTransaction(1L, createTransactionDTO);
			
			verify(createTransaction).createTransaction(
				new TransactionCreatedEvent(
					1L,
					2L,
					new BigDecimal("50.00"),
					"EUR",
					"USD"
				)
			);
		}
	}
	
	@Nested
	@DisplayName("Test Failed Create Transaction")
	class failedCreateTransaction {
		
		@Test
		@DisplayName("Debe lanzar excepción si el balance es insuficiente")
		void debeLanzarExcepcionPorBalanceInsuficiente() {
			sender = UserProfile.fromEntity(
					1L,
					10L,
					"sender",
					"sender@gmail.com",
					"Sender User",
					new BigDecimal("10.00"),
					"EUR"
			);
			
			when(upr.findById(1L)).thenReturn(sender);
			
			IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> createTransactionService.createTransaction(1L, createTransactionDTO)
			);
			
			assertEquals(
				"Balance insuficiente para realizar la transacción",
				exception.getMessage()
			);
		}
	}
}
