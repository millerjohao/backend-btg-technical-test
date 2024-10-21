package com.seti.btg.infrastructure.adapter;

import com.seti.btg.application.service.EmailService;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailNotificationAdapterTest {
    @InjectMocks
    private EmailNotificationAdapter emailNotificationAdapter;

    @Mock
    private EmailService emailService;

    @Test
    void testSendNotification_success() {
        // Dado
        CustomerEntity customer = new CustomerEntity();
        customer.setEmail("test@domain.com");
        customer.setName("John Doe");

        FundEntity fund = new FundEntity();
        fund.setName("Investment Fund");

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(BigDecimal.valueOf(1000.0));

        String message = "Test Message";

        // Cuando
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        emailNotificationAdapter.sendNotification(customer, fund, transaction, message);

        // Entonces
        verify(emailService).sendEmail(eq("test@domain.com"),
                eq("Suscripción exitosa al fondo Investment Fund"),
                eq("Hola John Doe, te has suscrito exitosamente al fondo Investment Fund con un monto de COP 1000.0."));
    }

    @Test
    void testSendNotification_emailServiceThrowsException() {
        // Dado
        CustomerEntity customer = new CustomerEntity();
        customer.setEmail("test@domain.com");
        customer.setName("John Doe");

        FundEntity fund = new FundEntity();
        fund.setName("Investment Fund");

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(BigDecimal.valueOf(1000.0));

        String message = "Test Message";

        // Configuramos el mock para que lance una excepción
        doThrow(new RuntimeException("Email sending failed")).when(emailService).sendEmail(anyString(), anyString(), anyString());

        // Cuando y Entonces
        Exception exception = assertThrows(RuntimeException.class, () -> {
            emailNotificationAdapter.sendNotification(customer, fund, transaction, message);
        });

        assertEquals("Email sending failed", exception.getMessage());
    }
}
