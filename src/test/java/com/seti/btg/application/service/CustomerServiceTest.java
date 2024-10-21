package com.seti.btg.application.service;

import com.seti.btg.application.mapper.CustomerDtoMapper;
import com.seti.btg.application.mapper.CustomerRequestMapper;
import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.dto.CustomerDto;
import com.seti.btg.domain.model.enumerator.NotificationType;
import com.seti.btg.domain.model.request.CustomerRequest;
import com.seti.btg.domain.repository.CustomerRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepositoryPort repository;

    @Mock
    private CustomerDtoMapper dtoMapper;

    @Mock
    private CustomerRequestMapper requestMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    public void testCreateNewUser_success() {
        // Arrange
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("John");
        customerRequest.setEmail("john.doe@example.com");
        customerRequest.setPhone("1234567890");
        customerRequest.setNotificationType(NotificationType.EMAIL);

        Customer newCustomer = new Customer();
        newCustomer.setName("John");
        newCustomer.setEmail("john.doe@example.com");
        newCustomer.setPhone("1234567890");
        newCustomer.setNotificationType(NotificationType.EMAIL);

        Customer createdCustomer = new Customer();
        createdCustomer.setId(1L);
        createdCustomer.setName("John");
        createdCustomer.setEmail("john.doe@example.com");
        createdCustomer.setPhone("1234567890");
        createdCustomer.setBalance(BigDecimal.valueOf(100.00));
        createdCustomer.setNotificationType(NotificationType.EMAIL);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setName("John");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setPhone("1234567890");
        customerDto.setBalance(BigDecimal.valueOf(100.00));
        customerDto.setNotificationType(NotificationType.EMAIL);

        // Simulamos los métodos de los mocks
        when(requestMapper.toDomain(customerRequest)).thenReturn(newCustomer);
        when(repository.createNewCustomer(newCustomer)).thenReturn(createdCustomer);
        when(dtoMapper.toDto(createdCustomer)).thenReturn(customerDto);

        // Act
        CustomerDto result = customerService.createNewUser(customerRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("1234567890", result.getPhone());
        assertEquals(BigDecimal.valueOf(100.00), result.getBalance());
        assertEquals(NotificationType.EMAIL, result.getNotificationType());
    }

    @Test
    public void testGetAll_success() {
        // Arrange
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John");
        customer1.setEmail("john.doe@example.com");
        customer1.setPhone("1234567890");
        customer1.setBalance(BigDecimal.valueOf(100.00));
        customer1.setNotificationType(NotificationType.EMAIL);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane");
        customer2.setEmail("jane.doe@example.com");
        customer2.setPhone("0987654321");
        customer2.setBalance(BigDecimal.valueOf(200.00));
        customer2.setNotificationType(NotificationType.SMS);

        List<Customer> customerList = Arrays.asList(customer1, customer2);

        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setId(1L);
        customerDto1.setName("John");
        customerDto1.setEmail("john.doe@example.com");
        customerDto1.setPhone("1234567890");
        customerDto1.setBalance(BigDecimal.valueOf(100.00));
        customerDto1.setNotificationType(NotificationType.EMAIL);

        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.setId(2L);
        customerDto2.setName("Jane");
        customerDto2.setEmail("jane.doe@example.com");
        customerDto2.setPhone("0987654321");
        customerDto2.setBalance(BigDecimal.valueOf(200.00));
        customerDto2.setNotificationType(NotificationType.SMS);

        List<CustomerDto> customerDtoList = Arrays.asList(customerDto1, customerDto2);

        // Simulamos los métodos de los mocks
        when(repository.getAll()).thenReturn(customerList);
        when(dtoMapper.toDto(customer1)).thenReturn(customerDto1);
        when(dtoMapper.toDto(customer2)).thenReturn(customerDto2);

        // Act
        List<CustomerDto> result = customerService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getName());
        assertEquals(BigDecimal.valueOf(100.00), result.get(0).getBalance());
        assertEquals(NotificationType.EMAIL, result.get(0).getNotificationType());
        assertEquals(NotificationType.SMS, result.get(1).getNotificationType());
    }

    @Test
    public void testGetUserById_success() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customer.setBalance(BigDecimal.valueOf(100.00));
        customer.setNotificationType(NotificationType.EMAIL);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setName("John");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setPhone("1234567890");
        customerDto.setBalance(BigDecimal.valueOf(100.00));
        customerDto.setNotificationType(NotificationType.EMAIL);

        // Simulamos los métodos de los mocks
        when(repository.getCustomerById(customerId)).thenReturn(customer);
        when(dtoMapper.toDto(customer)).thenReturn(customerDto);

        // Act
        CustomerDto result = customerService.getUserById(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("1234567890", result.getPhone());
        assertEquals(BigDecimal.valueOf(100.00), result.getBalance());
        assertEquals(NotificationType.EMAIL, result.getNotificationType());
    }
}
