package com.seti.btg.domain.constant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ErrorConstantTest  {
    @Test
    public void testCustomerAlreadyExists() {
        Assertions.assertEquals("Cliente ya existente", ErrorConstant.CUSTOMER_ALREADY_EXISTS);
    }

    @Test
    public void testCustomerNotFound() {
        String customerId = "12345";
        String expectedMessage = String.format("No existe el usuario con el id %s", customerId);

        Assertions.assertEquals(expectedMessage, String.format(ErrorConstant.CUSTOMER_NOT_FOUND, customerId));
    }

    @Test
    public void testFundNotFound() {
        String fundId = "67890";
        String expectedMessage = String.format("No existe fondo con el id %s", fundId);

        Assertions.assertEquals(expectedMessage, String.format(ErrorConstant.FUND_NOT_FOUND, fundId));
    }

    @Test
    public void testFundAlreadyExists() {
        Assertions.assertEquals("Fondo ya existente", ErrorConstant.FUND_ALREADY_EXISTS);
    }

    @Test
    public void testCustomerNotInFund() {
        Assertions.assertEquals("El cliente no está asociado al fondo", ErrorConstant.CUSTOMER_NOT_IN_FUND);
    }

    @Test
    public void testUnfundedSubscription() {
        String fund = "Fondo A";
        String expectedMessage = String.format("No tiene saldo disponible para vincularse al fondo %s", fund);

        Assertions.assertEquals(expectedMessage, String.format(ErrorConstant.UNFUNDED_SUBSCRIPTION, fund));
    }

    @Test
    public void testEndpointNotImplemented() {
        Assertions.assertEquals("Endpoint no implementado", ErrorConstant.ENDPOINT_NOT_IMPLEMENTED);
    }

    @Test
    public void testNotValidNumber() {
        Assertions.assertEquals("Número no verificado por capa gratuita", ErrorConstant.NOT_VALID_NUMBER);
    }

    @Test
    public void testUserAlreadyInFund() {
        Assertions.assertEquals("El cliente ya está vinculado a este fondo", ErrorConstant.USER_ALREADY_IN_FUND);
    }

    @Test
    public void testMinAmountNotReached() {
        Assertions.assertEquals("Monto mínimo de fondo no alcanzado", ErrorConstant.MIN_AMOUNT_NO_REACHED);
    }
}
