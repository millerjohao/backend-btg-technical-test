package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.request.FundRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class FundRequestMapperImplTest {

    private FundRequestMapperImpl fundRequestMapper;

    @BeforeEach
    public void setUp() {
        fundRequestMapper = new FundRequestMapperImpl();
    }

    @Test
    public void testToDomain_success() {
        // Arrange
        FundRequest fundRequest = new FundRequest();
        fundRequest.setName("Test Fund");
        fundRequest.setMinAmount(new BigDecimal("1000.00"));
        fundRequest.setCategory("Category A");

        // Act
        Fund fund = fundRequestMapper.toDomain(fundRequest);

        // Assert
        assertEquals("Test Fund", fund.getName());
        assertEquals(new BigDecimal("1000.00"), fund.getMinAmount());
        assertEquals("Category A", fund.getCategory());
    }

    @Test
    public void testToDomain_null() {
        // Act
        Fund fund = fundRequestMapper.toDomain(null);

        // Assert
        assertNull(fund);
    }
}
