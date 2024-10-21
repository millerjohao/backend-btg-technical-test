package com.seti.btg.application.service;

import com.seti.btg.application.mapper.TransactionDtoMapper;
import com.seti.btg.application.mapper.TransactionRequestMapper;
import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.Transaction;
import com.seti.btg.domain.model.dto.CustomerDto;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.dto.TransactionDto;
import com.seti.btg.domain.model.enumerator.TransactionType;
import com.seti.btg.domain.model.request.TransactionRequest;
import com.seti.btg.domain.repository.CustomerRepositoryPort;
import com.seti.btg.domain.repository.TransactionRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepositoryPort transactionRepositoryPort;

    @Mock
    private TransactionDtoMapper transactionDtoMapper;

    @Mock
    private TransactionRequestMapper requestMapper;

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewTransaction_success() {
        // Arrange
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(BigDecimal.valueOf(1000.00));
        transactionRequest.setIdCustomer(1L);
        transactionRequest.setIdFund(1L);

        // Inicializa Customer y Fund con valores válidos
        Customer customer = new Customer();
        customer.setId(1L);  // Asigna un ID de cliente válido

        Fund fund = new Fund();
        fund.setId(1L);  // Asigna un ID de fondo válido
        fund.setMinAmount(BigDecimal.valueOf(500.00));  // Inicializa el minAmount con un valor válido

        // Crea la transacción
        Transaction newTransaction = new Transaction();
        newTransaction.setId(UUID.randomUUID());
        newTransaction.setCustomer(customer);  // Asigna el Customer
        newTransaction.setFund(fund);  // Asigna el Fund
        newTransaction.setTransactionType(TransactionType.APERTURA);
        newTransaction.setTransactionDate(LocalDate.now());
        newTransaction.setAmount(BigDecimal.valueOf(1000.00));

        // Crea los DTOs correspondientes
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());

        FundDto fundDto = new FundDto();
        fundDto.setId(fund.getId());
        fundDto.setMinAmount(fund.getMinAmount().doubleValue());  // Convierte BigDecimal a Double
        fundDto.setCategory(fund.getCategory());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(newTransaction.getId());
        transactionDto.setCustomer(customerDto);
        transactionDto.setFund(fundDto);
        transactionDto.setTransactionType(TransactionType.APERTURA);
        transactionDto.setAmount(newTransaction.getAmount());
        transactionDto.setTransactionDate(newTransaction.getTransactionDate());

        // Simula los métodos de los mocks
        when(requestMapper.toDomain(transactionRequest)).thenReturn(newTransaction);
        when(transactionRepositoryPort.createNewTransaction(newTransaction)).thenReturn(newTransaction);
        when(transactionDtoMapper.toDto(newTransaction)).thenReturn(transactionDto);

        // Act
        TransactionDto result = transactionService.createNewTransaction(transactionRequest);

        // Assert
        assertNotNull(result);
        assertEquals(newTransaction.getId(), result.getId());
        assertEquals(TransactionType.APERTURA, result.getTransactionType());
        assertEquals(BigDecimal.valueOf(1000.00), result.getAmount());
        assertEquals(customerDto.getId(), result.getCustomer().getId());
        assertEquals(fundDto.getId(), result.getFund().getId());
    }

    @Test
    public void testGetAll_success() {
        // Arrange
        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID());
        transaction1.setAmount(BigDecimal.valueOf(1000.00));
        transaction1.setTransactionType(TransactionType.APERTURA);
        transaction1.setTransactionDate(LocalDate.now());

        Transaction transaction2 = new Transaction();
        transaction2.setId(UUID.randomUUID());
        transaction2.setAmount(BigDecimal.valueOf(500.00));
        transaction2.setTransactionType(TransactionType.CANCELACION);
        transaction2.setTransactionDate(LocalDate.now());

        // Inicializa los Customer y Fund con valores válidos
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Fund fund1 = new Fund();
        fund1.setId(1L);
        fund1.setMinAmount(BigDecimal.valueOf(500.00));  // Inicializa minAmount
        transaction1.setCustomer(customer1);
        transaction1.setFund(fund1);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        Fund fund2 = new Fund();
        fund2.setId(2L);
        fund2.setMinAmount(BigDecimal.valueOf(300.00));  // Inicializa minAmount
        transaction2.setCustomer(customer2);
        transaction2.setFund(fund2);

        // Crea los DTOs correspondientes
        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setId(customer1.getId());

        FundDto fundDto1 = new FundDto();
        fundDto1.setId(fund1.getId());
        fundDto1.setMinAmount(fund1.getMinAmount().doubleValue());  // Convierte BigDecimal a Double

        TransactionDto transactionDto1 = new TransactionDto();
        transactionDto1.setId(transaction1.getId());
        transactionDto1.setAmount(transaction1.getAmount());
        transactionDto1.setTransactionType(transaction1.getTransactionType());
        transactionDto1.setTransactionDate(transaction1.getTransactionDate());
        transactionDto1.setCustomer(customerDto1);
        transactionDto1.setFund(fundDto1);

        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.setId(customer2.getId());

        FundDto fundDto2 = new FundDto();
        fundDto2.setId(fund2.getId());
        fundDto2.setMinAmount(fund2.getMinAmount().doubleValue());  // Convierte BigDecimal a Double

        TransactionDto transactionDto2 = new TransactionDto();
        transactionDto2.setId(transaction2.getId());
        transactionDto2.setAmount(transaction2.getAmount());
        transactionDto2.setTransactionType(transaction2.getTransactionType());
        transactionDto2.setTransactionDate(transaction2.getTransactionDate());
        transactionDto2.setCustomer(customerDto2);
        transactionDto2.setFund(fundDto2);

        List<TransactionDto> transactionDtoList = Arrays.asList(transactionDto1, transactionDto2);

        // Simulamos los métodos de los mocks
        when(transactionRepositoryPort.getAll()).thenReturn(Arrays.asList(transaction1, transaction2));
        when(transactionDtoMapper.toDto(transaction1)).thenReturn(transactionDto1);
        when(transactionDtoMapper.toDto(transaction2)).thenReturn(transactionDto2);

        // Act
        List<TransactionDto> result = transactionService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(transaction1.getId(), result.get(0).getId());
        assertEquals(transaction2.getId(), result.get(1).getId());
    }

    @Test
    public void testGetTransactionsByUser_success() {
        // Arrange
        Long customerId = 1L;

        // Inicializa los Customer y Fund con valores válidos
        Customer customer1 = new Customer();
        customer1.setId(customerId);  // Asigna un ID de cliente válido

        Fund fund1 = new Fund();
        fund1.setId(1L);
        fund1.setMinAmount(BigDecimal.valueOf(500.00));  // Inicializa minAmount

        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID());
        transaction1.setAmount(BigDecimal.valueOf(1000.00));
        transaction1.setTransactionType(TransactionType.APERTURA);
        transaction1.setTransactionDate(LocalDate.now());
        transaction1.setCustomer(customer1);  // Asigna el Customer correctamente
        transaction1.setFund(fund1);  // Asigna el Fund correctamente

        // Inicializa el DTO correspondiente
        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setId(customer1.getId());

        FundDto fundDto1 = new FundDto();
        fundDto1.setId(fund1.getId());
        fundDto1.setMinAmount(fund1.getMinAmount().doubleValue());  // Convierte BigDecimal a Double

        TransactionDto transactionDto1 = new TransactionDto();
        transactionDto1.setId(transaction1.getId());
        transactionDto1.setAmount(transaction1.getAmount());
        transactionDto1.setTransactionType(transaction1.getTransactionType());
        transactionDto1.setTransactionDate(transaction1.getTransactionDate());
        transactionDto1.setCustomer(customerDto1);
        transactionDto1.setFund(fundDto1);

        // Simula el método de mock para obtener las transacciones por usuario
        when(transactionRepositoryPort.getTransactionsByUser(customerId)).thenReturn(Arrays.asList(transaction1));
        when(transactionDtoMapper.toDto(transaction1)).thenReturn(transactionDto1);

        // Act
        List<TransactionDto> result = transactionService.getTransactionsByUser(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transaction1.getId(), result.get(0).getId());
        assertEquals(transaction1.getAmount(), result.get(0).getAmount());
        assertEquals(transaction1.getCustomer().getId(), result.get(0).getCustomer().getId());  // Verifica que el ID del cliente no sea nulo
    }



    @Test
    public void testCancelTransaction_success() {
        // Arrange
        Long customerId = 1L;
        Long fundId = 1L;

        Customer customer = new Customer();
        customer.setId(customerId);

        Fund fund = new Fund();
        fund.setId(fundId);

        Transaction canceledTransaction = new Transaction();
        canceledTransaction.setId(UUID.randomUUID());
        canceledTransaction.setCustomer(customer);
        canceledTransaction.setFund(fund);
        canceledTransaction.setTransactionType(TransactionType.CANCELACION);
        canceledTransaction.setTransactionDate(LocalDate.now());
        canceledTransaction.setAmount(BigDecimal.valueOf(500.00));

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());

        FundDto fundDto = new FundDto();
        fundDto.setId(fund.getId());

        TransactionDto canceledTransactionDto = new TransactionDto();
        canceledTransactionDto.setId(canceledTransaction.getId());
        canceledTransactionDto.setAmount(canceledTransaction.getAmount());
        canceledTransactionDto.setTransactionType(canceledTransaction.getTransactionType());
        canceledTransactionDto.setTransactionDate(canceledTransaction.getTransactionDate());
        canceledTransactionDto.setCustomer(customerDto);
        canceledTransactionDto.setFund(fundDto);

        // Simulamos los métodos de los mocks
        when(transactionRepositoryPort.cancelTransaction(customerId, fundId)).thenReturn(canceledTransaction);
        when(transactionDtoMapper.toDto(canceledTransaction)).thenReturn(canceledTransactionDto);

        // Act
        TransactionDto result = transactionService.cancelTransaction(customerId, fundId);

        // Assert
        assertNotNull(result);
        assertEquals(canceledTransaction.getId(), result.getId());
        assertEquals(TransactionType.CANCELACION, result.getTransactionType());
        assertEquals(canceledTransaction.getAmount(), result.getAmount());
    }
}
