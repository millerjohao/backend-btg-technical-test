package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.enumerator.NotificationType;
import com.seti.btg.domain.model.request.CustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerRequestMapperImplTest {

    private CustomerRequestMapperImpl customerRequestMapper;

    @BeforeEach
    public void setUp() {
        customerRequestMapper = new CustomerRequestMapperImpl();
    }

    @Test
    public void testToDomain_success() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String phone = "123-456-7890";

        CustomerRequest request = new CustomerRequest();
        request.setName(name);
        request.setEmail(email);
        request.setPhone(phone);
        request.setNotificationType(NotificationType.EMAIL);

        // Act
        Customer customer = customerRequestMapper.toDomain(request);

        // Assert
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
        assertEquals(NotificationType.EMAIL, customer.getNotificationType());
    }

    @Test
    public void testToDomain_nullRequest() {
        // Act
        Customer customer = customerRequestMapper.toDomain(null);

        // Assert
        assertEquals(null, customer);
    }
}