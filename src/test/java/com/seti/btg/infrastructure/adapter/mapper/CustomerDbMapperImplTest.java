package com.seti.btg.infrastructure.adapter.mapper;

import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.enumerator.NotificationType;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerDbMapperImplTest {

    private CustomerDbMapperImpl customerDbMapper;
    private Customer mockCustomer;
    private CustomerEntity mockCustomerEntity;

    @BeforeEach
    public void setUp() {
        customerDbMapper = new CustomerDbMapperImpl();

        mockCustomer = mock(Customer.class);
        mockCustomerEntity = mock(CustomerEntity.class);
    }

    @Test
    public void testToDbo_nonNullDomain() {
        // Configura el mock de Customer con valores no nulos
        Long customerId = 1L;
        when(mockCustomer.getId()).thenReturn(customerId);
        when(mockCustomer.getName()).thenReturn("John Doe");
        when(mockCustomer.getEmail()).thenReturn("john.doe@example.com");
        when(mockCustomer.getPhone()).thenReturn("123456789");
        when(mockCustomer.getBalance()).thenReturn(BigDecimal.valueOf(1000.0));
        when(mockCustomer.getNotificationType()).thenReturn(NotificationType.SMS);

        // Llama al método toDbo con un objeto Customer no nulo
        CustomerEntity customerEntity = customerDbMapper.toDbo(mockCustomer);

        // Verifica que el objeto CustomerEntity no sea null
        assertNotNull(customerEntity, "El resultado no debe ser null cuando el domain no es null");

        // Verifica que los valores se mapeen correctamente
        assertEquals(customerId, customerEntity.getId());
        assertEquals("John Doe", customerEntity.getName());
        assertEquals("john.doe@example.com", customerEntity.getEmail());
        assertEquals("123456789", customerEntity.getPhone());
        assertEquals(NotificationType.SMS, customerEntity.getNotificationType());
    }

    @Test
    public void testToDbo_nullDomain() {
        // Llama al método toDbo con un objeto Customer nulo
        CustomerEntity customerEntity = customerDbMapper.toDbo(null);

        // Verifica que el resultado sea null cuando el domain es nulo
        assertNull(customerEntity, "El resultado debe ser null cuando el domain es null");
    }

    @Test
    public void testToDomain_nonNullEntity() {
        // Configura el mock de CustomerEntity con valores no nulos
        Long customerId = 1L;
        when(mockCustomerEntity.getId()).thenReturn(customerId);
        when(mockCustomerEntity.getName()).thenReturn("John Doe");
        when(mockCustomerEntity.getEmail()).thenReturn("john.doe@example.com");
        when(mockCustomerEntity.getPhone()).thenReturn("123456789");
        when(mockCustomerEntity.getBalance()).thenReturn(BigDecimal.valueOf(1000.0));
        when(mockCustomerEntity.getNotificationType()).thenReturn(NotificationType.SMS);

        // Llama al método toDomain con un objeto CustomerEntity no nulo
        Customer customer = customerDbMapper.toDomain(mockCustomerEntity);

        // Verifica que el objeto Customer no sea null
        assertNotNull(customer, "El resultado no debe ser null cuando el entity no es null");

        // Verifica que los valores se mapeen correctamente
        assertEquals(customerId, customer.getId());
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("123456789", customer.getPhone());
        assertEquals(NotificationType.SMS, customer.getNotificationType());
    }

    @Test
    public void testToDomain_nullEntity() {
        // Llama al método toDomain con un objeto CustomerEntity nulo
        Customer customer = customerDbMapper.toDomain(null);

        // Verifica que el resultado sea null cuando el entity es nulo
        assertNull(customer, "El resultado debe ser null cuando el entity es null");
    }
}

