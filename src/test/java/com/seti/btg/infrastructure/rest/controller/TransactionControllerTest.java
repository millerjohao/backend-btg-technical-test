package com.seti.btg.infrastructure.rest.controller;

import com.seti.btg.application.service.TransactionService;
import com.seti.btg.domain.model.dto.TransactionDto;
import com.seti.btg.domain.model.request.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {
    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionDto transactionDto;

    @Mock
    private TransactionRequest transactionRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTransactions() {
        // Arrange
        List<TransactionDto> transactions = Arrays.asList(transactionDto);
        when(transactionService.getAll()).thenReturn(transactions);

        // Act
        List<TransactionDto> result = transactionController.getAllTransactions();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transactionDto, result.get(0));
        verify(transactionService, times(1)).getAll();
    }

    @Test
    public void testCreateSubscription() {
        // Arrange
        when(transactionService.createNewTransaction(any(TransactionRequest.class))).thenReturn(transactionDto);

        // Act
        TransactionDto result = transactionController.createSubscription(transactionRequest);

        // Assert
        assertNotNull(result);
        assertEquals(transactionDto, result);
        verify(transactionService, times(1)).createNewTransaction(transactionRequest);
    }

    @Test
    public void testGetTransactionsByUser() {
        // Arrange
        long customerId = 1L;
        List<TransactionDto> transactions = Arrays.asList(transactionDto);
        when(transactionService.getTransactionsByUser(customerId)).thenReturn(transactions);

        // Act
        List<TransactionDto> result = transactionController.getTransactionsByUser(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transactionDto, result.get(0));
        verify(transactionService, times(1)).getTransactionsByUser(customerId);
    }

    @Test
    public void testCancelTransaction() {
        // Arrange
        long customerId = 1L;
        long fundId = 101L;
        when(transactionService.cancelTransaction(customerId, fundId)).thenReturn(transactionDto);

        // Act
        TransactionDto result = transactionController.cancelTransaction(customerId, fundId);

        // Assert
        assertNotNull(result);
        assertEquals(transactionDto, result);
        verify(transactionService, times(1)).cancelTransaction(customerId, fundId);
    }
}
