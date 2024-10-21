package com.seti.btg.infrastructure.adapter;

import com.seti.btg.domain.model.Customer;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import com.seti.btg.infrastructure.adapter.mapper.CustomerDbMapper;
import com.seti.btg.infrastructure.adapter.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerJpaAdapterTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerDbMapper dbMapper;

    @InjectMocks
    private CustomerJpaAdapter customerJpaAdapter;

    private Customer customer;
    private CustomerEntity customerEntity;
    private CustomerEntity savedCustomerEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("john.doe@example.com");
        customer.setPhone("123456789");
        customer.setName("John Doe");

        customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setEmail("john.doe@example.com");
        customerEntity.setPhone("123456789");
        customerEntity.setName("John Doe");

        savedCustomerEntity = new CustomerEntity();
        savedCustomerEntity.setId(1L);
        savedCustomerEntity.setEmail("john.doe@example.com");
        savedCustomerEntity.setPhone("123456789");
        savedCustomerEntity.setName("John Doe");
    }

    @Test
    void testCreateNewCustomer_Success() {

        when(repository.existsByEmail(customer.getEmail())).thenReturn(false);
        when(repository.existsByPhone(customer.getPhone())).thenReturn(false);
        when(dbMapper.toDbo(customer)).thenReturn(customerEntity);
        when(repository.save(customerEntity)).thenReturn(savedCustomerEntity);
        when(dbMapper.toDomain(savedCustomerEntity)).thenReturn(customer);


        Customer result = customerJpaAdapter.createNewCustomer(customer);


        assertNotNull(result, "El cliente creado no debe ser null");
        assertEquals(customer.getId(), result.getId());
        assertEquals(customer.getEmail(), result.getEmail());
        assertEquals(customer.getPhone(), result.getPhone());


        verify(repository, times(1)).save(customerEntity);
    }

    @Test
    void testCreateNewCustomer_CustomerAlreadyExists() {

        when(repository.existsByEmail(customer.getEmail())).thenReturn(true);


        ErrorException exception = assertThrows(ErrorException.class, () -> customerJpaAdapter.createNewCustomer(customer));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());

        verify(repository, never()).save(any());
    }

    @Test
    void testGetCustomerById_Found() {

        when(repository.findById(1L)).thenReturn(Optional.of(savedCustomerEntity));
        when(dbMapper.toDomain(savedCustomerEntity)).thenReturn(customer);


        Customer result = customerJpaAdapter.getCustomerById(1L);


        assertNotNull(result, "El cliente no debe ser null");
        assertEquals(customer.getId(), result.getId());


        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetCustomerById_NotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());


        ErrorException exception = assertThrows(ErrorException.class, () -> customerJpaAdapter.getCustomerById(1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetAllCustomers() {

        when(repository.findAll()).thenReturn(List.of(savedCustomerEntity));
        when(dbMapper.toDomain(savedCustomerEntity)).thenReturn(customer);


        List<Customer> result = customerJpaAdapter.getAll();


        assertNotNull(result, "La lista de clientes no debe ser null");
        assertEquals(1, result.size());
        assertEquals(customer.getId(), result.get(0).getId());


        verify(repository, times(1)).findAll();
    }

    @Test
    void testCreateNewCustomer_EmailExists() {

        when(repository.existsByEmail(customer.getEmail())).thenReturn(true);
        when(repository.existsByPhone(customer.getPhone())).thenReturn(false);


        ErrorException exception = assertThrows(ErrorException.class, () -> customerJpaAdapter.createNewCustomer(customer));


        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());



        verify(repository, times(1)).existsByEmail(customer.getEmail());
    }

    @Test
    void testCreateNewCustomer_PhoneExists() {

        when(repository.existsByEmail(customer.getEmail())).thenReturn(false);
        when(repository.existsByPhone(customer.getPhone())).thenReturn(true);


        ErrorException exception = assertThrows(ErrorException.class, () -> customerJpaAdapter.createNewCustomer(customer));


        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());



        verify(repository, times(1)).existsByEmail(customer.getEmail());
        verify(repository, times(1)).existsByPhone(customer.getPhone());
    }

    @Test
    void testCreateNewCustomer_EmailAndPhoneExists() {

        when(repository.existsByEmail(customer.getEmail())).thenReturn(true);
        when(repository.existsByPhone(customer.getPhone())).thenReturn(true);


        ErrorException exception = assertThrows(ErrorException.class, () -> customerJpaAdapter.createNewCustomer(customer));


        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());



        verify(repository, times(1)).existsByEmail(customer.getEmail());
    }

    @Test
    void testCreateNewCustomer_NoConflict() {

        when(repository.existsByEmail(customer.getEmail())).thenReturn(false);
        when(repository.existsByPhone(customer.getPhone())).thenReturn(false);


        assertDoesNotThrow(() -> customerJpaAdapter.createNewCustomer(customer));


        verify(repository, times(1)).existsByEmail(customer.getEmail());
        verify(repository, times(1)).existsByPhone(customer.getPhone());
    }
}
