package com.paylink.transation.application.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import com.paylink.kafka.events.TransactionFailedEvent;
import com.paylink.transation.application.port.out.TransactionFailedPublisher;
import com.paylink.transation.application.port.out.TransactionRepository;
import com.paylink.transation.domain.model.Transaction;
import com.paylink.transation.domain.model.TransactionStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test TransactionValidator Service")
public class TransactionValidatorServiceTest {

    @Mock
    private TransactionRepository tr;

    @Mock
    private TransactionFailedPublisher transactionFailed;

    @InjectMocks
    private TransactionValidatorService validatorService;

    private Transaction validTransaction;

    @BeforeEach
    void setUp() {
        validTransaction = Transaction.fromEntity(
                1L,
                10L,
                20L,
                new BigDecimal("100.00"),
                "EUR",
                "USD",
                TransactionStatus.PROCESSING,
                LocalDateTime.now()
        );
    }

    @Nested
    @DisplayName("Transacción válida")
    class validTransaction {

        @Test
        @DisplayName("Debe devolver true y no llamar a failed ni publicar evento")
        void debeDevolverTrue() {
            boolean result = validatorService.validate(validTransaction);

            assertTrue(result);
            verify(tr, never()).markAsFailed(validTransaction.getId());
            verify(transactionFailed, never()).publishTransactionFailed(org.mockito.ArgumentMatchers.any(TransactionFailedEvent.class));
        }
    }

    @Nested
    @DisplayName("Sender o Receiver nulos")
    class senderReceiverNulos {

        @Test
        @DisplayName("Debe fallar si sender es null")
        void senderNull() {
            Transaction t = Transaction.createTransaction(
                    null,
                    20L,
                    new BigDecimal("100.00"),
                    "EUR",
                    "USD",
                    TransactionStatus.FAILED,
                    LocalDateTime.now()
            );

            boolean result = validatorService.validate(t);

            assertFalse(result);
            verify(tr).markAsFailed(t.getId());
            verify(transactionFailed).publishTransactionFailed(org.mockito.ArgumentMatchers.any(TransactionFailedEvent.class));
        }

        @Test
        @DisplayName("Debe fallar si receiver es null")
        void receiverNull() {
            Transaction t = Transaction.createTransaction(
                    10L,
                    null,
                    new BigDecimal("100.00"),
                    "EUR",
                    "USD",
                    TransactionStatus.FAILED,
                    LocalDateTime.now()
            );

            boolean result = validatorService.validate(t);

            assertFalse(result);
            verify(tr).markAsFailed(t.getId());
            verify(transactionFailed).publishTransactionFailed(org.mockito.ArgumentMatchers.any(TransactionFailedEvent.class));
        }
    }

    @Nested
    @DisplayName("Sender igual a Receiver")
    class senderEqualsReceiver {

        @Test
        @DisplayName("Debe fallar si sender = receiver")
        void senderEquivaleReceiver() {
            Transaction t = Transaction.createTransaction(
                    10L,
                    10L,
                    new BigDecimal("100.00"),
                    "EUR",
                    "USD",
                    TransactionStatus.FAILED,
                    LocalDateTime.now()
            );

            boolean result = validatorService.validate(t);

            assertFalse(result);
            verify(tr).markAsFailed(t.getId());
            verify(transactionFailed).publishTransactionFailed(org.mockito.ArgumentMatchers.any(TransactionFailedEvent.class));
        }
    }

    @Nested
    @DisplayName("Amount inválido")
    class amountInvalido {

        @Test
        @DisplayName("Debe fallar si amount es 0")
        void amountCero() {
            Transaction t = Transaction.createTransaction(
                    10L,
                    20L,
                    BigDecimal.ZERO,
                    "EUR",
                    "USD",
                    TransactionStatus.FAILED,
                    LocalDateTime.now()
            );

            boolean result = validatorService.validate(t);

            assertFalse(result);
            verify(tr).markAsFailed(t.getId());
            verify(transactionFailed).publishTransactionFailed(org.mockito.ArgumentMatchers.any(TransactionFailedEvent.class));
        }

        @Test
        @DisplayName("Debe fallar si amount es negativo")
        void amountNegativo() {
            Transaction t = Transaction.createTransaction(
                    10L,
                    20L,
                    new BigDecimal("-50"),
                    "EUR",
                    "USD",
                    TransactionStatus.FAILED,
                    LocalDateTime.now()
            );

            boolean result = validatorService.validate(t);

            assertFalse(result);
            verify(tr).markAsFailed(t.getId());
            verify(transactionFailed).publishTransactionFailed(org.mockito.ArgumentMatchers.any(TransactionFailedEvent.class));
        }

        @Test
        @DisplayName("Debe fallar si amount es null")
        void amountNull() {
            Transaction t = Transaction.createTransaction(
                    10L,
                    20L,
                    null,
                    "EUR",
                    "USD",
                    TransactionStatus.FAILED,
                    LocalDateTime.now()
            );

            boolean result = validatorService.validate(t);

            assertFalse(result);
            verify(tr).markAsFailed(t.getId());
            verify(transactionFailed).publishTransactionFailed(org.mockito.ArgumentMatchers.any(TransactionFailedEvent.class));
        }
    }
}
