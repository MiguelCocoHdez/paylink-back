package com.paylink.exchange.application.service;

import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.exchange.application.port.out.ExchangeSuccessPublisher;
import com.paylink.kafka.events.ExchangeSuccessEvent;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test ExchangeAndSend Service")
public class ExchangeAndSendServiceTest {

    @Mock
    private ExchangeSuccessPublisher exchangeSuccessPublisher;

    @InjectMocks
    private ExchangeAndSendService service;

    private ExchangeSuccessEvent event;

    @BeforeEach
    void setUp() {
        event = new ExchangeSuccessEvent(
        		1L,
        		2L,
        		3L,
        		"EUR",
        		"USD",
        		BigDecimal.TEN,
        		BigDecimal.valueOf(300.00)
        );
    }

    @Test
    @DisplayName("Debe publicar el evento de exchange exitoso")
    void debePublicarEvento() {
        service.sendExchange(event);

        verify(exchangeSuccessPublisher).publishExchangeSuccess(event);
    }
}
