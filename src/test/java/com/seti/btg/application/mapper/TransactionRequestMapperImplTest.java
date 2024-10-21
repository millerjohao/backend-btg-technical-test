package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.Transaction;
import com.seti.btg.domain.model.request.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionRequestMapperImplTest {

    private TransactionRequestMapperImpl transactionRequestMapper;

    @BeforeEach
    public void setUp() {
        transactionRequestMapper = new TransactionRequestMapperImpl();
    }

    @Test
    public void testToDomain_success() {
        // Arrange
        Long idCustomer = 1L;
        Long idFund = 2L;
        BigDecimal amount = new BigDecimal("100.00");

        TransactionRequest request = new TransactionRequest();
        request.setIdCustomer(idCustomer);
        request.setIdFund(idFund);
        request.setAmount(amount);

        // Act
        Transaction transaction = transactionRequestMapper.toDomain(request);

        // Assert
        assertEquals(idCustomer, transaction.getCustomer().getId());
        assertEquals(idFund, transaction.getFund().getId());
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    public void testMapCustomer_null() {
        // Act
        Customer customer = transactionRequestMapper.mapCustomer(null);

        // Assert
        assertEquals(null, customer);
    }

    @Test
    public void testMapCustomer_success() {
        // Arrange
        Long customerId = 1L;

        // Act
        Customer customer = transactionRequestMapper.mapCustomer(customerId);

        // Assert
        assertEquals(customerId, customer.getId());
    }

    @Test
    public void testMapFund_null() {
        // Act
        Fund fund = transactionRequestMapper.mapFund(null);

        // Assert
        assertEquals(null, fund);
    }

    @Test
    public void testMapFund_success() {
        // Arrange
        Long fundId = 2L;

        // Act
        Fund fund = transactionRequestMapper.mapFund(fundId);

        // Assert
        assertEquals(fundId, fund.getId());
    }
}

