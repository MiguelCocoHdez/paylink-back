package com.paylink.transation.application.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.kafka.events.TransactionExchangeEvent;
import com.paylink.transation.application.port.out.TransactionExchangePublisher;
import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.service.TransactionValidator;
import com.paylink.transation.domain.model.TransactionStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test ProcessCurrencyExchangeTransaction Service")
public class ProccessCurrencyExchangeTransactionServiceTest {

	@Mock
	private TransactionValidator validator;
	
	@Mock
	private TransactionRepository tr;
	
	@Mock
	private TransactionExchangePublisher transactionExchangePublisher;
	
	@InjectMocks
	private ProcessCurrencyExchangeTransactionService service;
	
	private Transaction transaction;
	
	@BeforeEach
	void setUp() {
		transaction = Transaction.fromEntity(
				1L,
				10L,
				20L,
				new BigDecimal("100.00"),
				"EUR",
				"USD",
				TransactionStatus.PENDING,
				LocalDateTime.now()
		);
	}
	
	@Nested
	@DisplayName("Test Success Transaction")
	class successTransaction {
		
		@BeforeEach
		void setUpSuccess() {
			when(validator.validate(transaction)).thenReturn(true);
		}
		
		@Test
		@DisplayName("Debe marcar como processing y enviar evento de exchange")
		void debeMarcarYEnviarEvento() {
			service.validateAndSendToExchange(transaction);
			
			verify(tr).markAsProcessing(transaction.getId());
			verify(transactionExchangePublisher).transactionExchange(org.mockito.ArgumentMatchers.any(TransactionExchangeEvent.class));
		}
	}
	
	@Nested
	@DisplayName("Test Failed Transaction")
	class failedTransaction {
		
		@BeforeEach
		void setUpFail() {
			when(validator.validate(transaction)).thenReturn(false);
		}
		
		@Test
		@DisplayName("NO debe marcar ni enviar evento si transacción inválida")
		void noDebeMarcarNiEnviarEvento() {
			service.validateAndSendToExchange(transaction);
			
			verify(tr, never()).markAsProcessing(transaction.getId());
			verify(transactionExchangePublisher, never()).transactionExchange(org.mockito.ArgumentMatchers.any(TransactionExchangeEvent.class));
		}
	}
}
