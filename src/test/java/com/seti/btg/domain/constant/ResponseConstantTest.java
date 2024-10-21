package com.seti.btg.domain.constant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ResponseConstantTest {

    @Test
    public void testSubscriptionTitle() {
        // Verificar que la constante tiene el valor esperado
        assertEquals("Suscripción exitosa al fondo ", ResponseConstant.SUBSCRIPTION_TITLE);
    }

    @Test
    public void testSubscriptionBody() {
        // Verificar que la constante tiene el valor esperado
        String expected = "Hola %s, te has suscrito exitosamente al fondo %s con un monto de COP %s.";
        assertEquals(expected, ResponseConstant.SUBSCRIPTION_BODY);
    }

    @Test
    public void testSuccessfullySubscription() {
        // Verificar que la constante tiene el valor esperado
        String expected = "Te has suscrito exitosamente al fondo %s.";
        assertEquals(expected, ResponseConstant.SUCCESSFULLY_SUBSCRIPTION_);
    }
}
