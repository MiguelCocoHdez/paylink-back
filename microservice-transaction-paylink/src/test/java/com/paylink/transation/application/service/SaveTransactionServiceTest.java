package com.paylink.transation.application.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.model.TransactionStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test SaveTransaction Service")
public class SaveTransactionServiceTest {

	@Mock
	private TransactionRepository tr;
	
	@InjectMocks
	private SaveTransactionService service;
	
	private Transaction transaction;
	private Transaction savedTransaction;
	
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
		
		savedTransaction = Transaction.fromEntity(
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
	
	@Test
	@DisplayName("Debe guardar la transacción y devolverla")
	void debeGuardarYDevolver() {
		when(tr.save(transaction)).thenReturn(savedTransaction);
		
		Transaction result = service.saveTransaction(transaction);
		
		verify(tr).save(transaction);
		assertEquals(savedTransaction, result);
	}
}
