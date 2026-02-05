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

import com.paylink.kafka.events.TransactionSuccessEvent;
import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.application.port.out.TransactionSuccessPublisher;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.model.TransactionStatus;
import com.paylink.transation.domain.service.TransactionValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test ProccessDirectTransaction Service")
public class ProccessDirectTransactionServiceTest {

	@Mock
	private TransactionValidator validator;
	
	@Mock
	private TransactionRepository tr;
	
	@Mock
	private TransactionSuccessPublisher transactionSuccess;
	
	@InjectMocks
	private ProccessDirectTransactionService proccessService;
	
	private Transaction transaction;
	
	@BeforeEach
	void setUp() {
		transaction = Transaction.fromEntity(
		        1L,                
		        10L,              
		        20L,               
		        new BigDecimal("50.00"),
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
		@DisplayName("Debe marcar la transacción como aceptada y publicar el evento")
		void debeMarcarYPublicar() {
			proccessService.validateTransaction(transaction);
			
			verify(tr).markAsAccepted(transaction.getId());
			verify(transactionSuccess).publishTransactionSuccess(org.mockito.ArgumentMatchers.any(TransactionSuccessEvent.class));
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
		@DisplayName("NO debe marcar la transacción ni publicar evento si no es válida")
		void noDebeMarcarNiPublicar() {
			proccessService.validateTransaction(transaction);
			
			verify(tr, never()).markAsAccepted(transaction.getId());
			verify(transactionSuccess, never()).publishTransactionSuccess(org.mockito.ArgumentMatchers.any(TransactionSuccessEvent.class));
		}
	}
}
