package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.Transaction;
import com.seti.btg.domain.model.dto.CustomerDto;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.dto.TransactionDto;
import com.seti.btg.domain.model.enumerator.NotificationType;
import com.seti.btg.domain.model.enumerator.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionDtoMapperImplTest {

    @InjectMocks
    private TransactionDtoMapperImpl transactionDtoMapper;

    @Mock
    private Customer customer;
    @Mock
    private Fund fund;

    @Test
    public void testToDto_success() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(1000.00);
        LocalDate transactionDate = LocalDate.now();
        TransactionType transactionType = TransactionType.APERTURA;

        // Mock de Customer
        when(customer.getId()).thenReturn(1L);
        when(customer.getName()).thenReturn("John Doe");
        when(customer.getEmail()).thenReturn("johndoe@example.com");
        when(customer.getPhone()).thenReturn("123456789");
        when(customer.getBalance()).thenReturn(BigDecimal.valueOf(1500.00));
        when(customer.getNotificationType()).thenReturn(NotificationType.EMAIL);

        // Mock de Fund
        when(fund.getId()).thenReturn(1L);
        when(fund.getName()).thenReturn("Fund A");
        when(fund.getMinAmount()).thenReturn(BigDecimal.valueOf(500.00));
        when(fund.getCategory()).thenReturn("Category A");

        // Creación de un objeto Transaction
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setCustomer(customer);  // Asocia el mock de Customer
        transaction.setFund(fund);  // Asocia el mock de Fund
        transaction.setTransactionType(transactionType);
        transaction.setTransactionDate(transactionDate);
        transaction.setAmount(amount);

        // Act
        TransactionDto transactionDto = transactionDtoMapper.toDto(transaction);

        // Assert
        assertNotNull(transactionDto);
        assertEquals(transactionId, transactionDto.getId());
        assertEquals(amount, transactionDto.getAmount());
        assertEquals(transactionDate, transactionDto.getTransactionDate());
        assertEquals(transactionType, transactionDto.getTransactionType());

        // Verifica que el CustomerDto fue correctamente mapeado
        assertNotNull(transactionDto.getCustomer());
        assertEquals(Long.valueOf(1), transactionDto.getCustomer().getId());
        assertEquals("John Doe", transactionDto.getCustomer().getName());
        assertEquals("johndoe@example.com", transactionDto.getCustomer().getEmail());

        // Verifica que el FundDto fue correctamente mapeado
        assertNotNull(transactionDto.getFund());
        assertEquals(Long.valueOf(1), transactionDto.getFund().getId());
        assertEquals("Fund A", transactionDto.getFund().getName());
        assertEquals(Double.valueOf(500.00), transactionDto.getFund().getMinAmount());
        assertEquals("Category A", transactionDto.getFund().getCategory());
    }

    @Test
    public void testToDto_nullTransaction() {
        // Act
        TransactionDto transactionDto = transactionDtoMapper.toDto(null);

        // Assert
        assertNull(transactionDto);
    }

    @Test
    public void testCustomerToCustomerDto_nullCustomer() {
        // Act
        CustomerDto customerDto = transactionDtoMapper.customerToCustomerDto(null);

        // Assert
        assertNull(customerDto);
    }

    @Test
    public void testFundToFundDto_nullFund() {
        // Act
        FundDto fundDto = transactionDtoMapper.fundToFundDto(null);

        // Assert
        assertNull(fundDto);
    }
}

