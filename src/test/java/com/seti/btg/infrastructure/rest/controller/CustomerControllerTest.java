package com.seti.btg.infrastructure.rest.controller;

import com.seti.btg.application.service.CustomerService;
import com.seti.btg.domain.model.dto.CustomerDto;
import com.seti.btg.domain.model.request.CustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerDto customerDto;

    @Mock
    private CustomerRequest customerRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomers() {
        // Arrange
        List<CustomerDto> customers = Arrays.asList(customerDto);
        when(customerService.getAll()).thenReturn(customers);

        // Act
        List<CustomerDto> result = customerController.getAllCustomers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(customerDto, result.get(0));
        verify(customerService, times(1)).getAll();
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        when(customerService.createNewUser(any(CustomerRequest.class))).thenReturn(customerDto);

        // Act
        CustomerDto result = customerController.createCustomer(customerRequest);

        // Assert
        assertNotNull(result);
        assertEquals(customerDto, result);
        verify(customerService, times(1)).createNewUser(customerRequest);
    }

    @Test
    public void testGetCustomerById() {
        // Arrange
        long customerId = 1L;
        when(customerService.getUserById(customerId)).thenReturn(customerDto);

        // Act
        CustomerDto result = customerController.getCustomerById(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(customerDto, result);
        verify(customerService, times(1)).getUserById(customerId);
    }
}
