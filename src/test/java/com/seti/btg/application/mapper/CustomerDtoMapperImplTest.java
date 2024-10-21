package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.dto.CustomerDto;
import com.seti.btg.domain.model.enumerator.NotificationType;
import com.seti.btg.domain.model.request.FundRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDtoMapperImplTest {

    private CustomerDtoMapperImpl customerDtoMapper;

    private FundRequestMapperImpl fundRequestMapper;

    @BeforeEach
    public void setUp() {
        customerDtoMapper = new CustomerDtoMapperImpl();
        fundRequestMapper = new FundRequestMapperImpl();
    }

    @Test
    public void testToDto_success() {
        // Arrange
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";
        String phone = "123-456-7890";
        BigDecimal balance = new BigDecimal("1000.00");

        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setBalance(balance);
        customer.setNotificationType(NotificationType.EMAIL);

        // Act
        CustomerDto customerDto = customerDtoMapper.toDto(customer);

        // Assert
        assertEquals(id, customerDto.getId());
        assertEquals(name, customerDto.getName());
        assertEquals(email, customerDto.getEmail());
        assertEquals(phone, customerDto.getPhone());
        assertEquals(balance, customerDto.getBalance());
        assertEquals(NotificationType.EMAIL, customerDto.getNotificationType());
    }

    @Test
    public void testToDto_nullDomain() {
        // Act
        CustomerDto customerDto = customerDtoMapper.toDto(null);

        // Assert
        assertEquals(null, customerDto);
    }


    @Test
    public void testToDomain_nullRequest() {
        // Actúa sobre el caso cuando request es null
        Fund fund = fundRequestMapper.toDomain(null);

        // Verifica que el resultado es null
        assertNull(fund, "El resultado debe ser null cuando la solicitud es null");
    }

    @Test
    public void testToDomain_nonNullRequest() {
        // Crea un objeto FundRequest para probar un caso no nulo
        FundRequest request = new FundRequest();
        request.setName("Fondo A");
        request.setMinAmount(BigDecimal.valueOf(1000));
        request.setCategory("Categoría A");

        // Actúa sobre el caso cuando request no es null
        Fund fund = fundRequestMapper.toDomain(request);

        // Verifica que el resultado no sea null
        assertNotNull(fund, "El resultado no debe ser null cuando la solicitud no es null");

        // Verifica que los valores de los campos se hayan mapeado correctamente
        assertEquals("Fondo A", fund.getName(), "El nombre del fondo no es correcto");
        assertEquals(BigDecimal.valueOf(1000), fund.getMinAmount(), "El monto mínimo no es correcto");
        assertEquals("Categoría A", fund.getCategory(), "La categoría no es correcta");
    }
}

