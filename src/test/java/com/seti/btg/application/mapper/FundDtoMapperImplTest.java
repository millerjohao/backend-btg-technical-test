package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.dto.FundDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FundDtoMapperImplTest {

    private FundDtoMapperImpl fundDtoMapper;

    @BeforeEach
    public void setUp() {
        fundDtoMapper = new FundDtoMapperImpl();
    }

    @Test
    public void testToDto_success() {
        // Arrange
        Long id = 1L;
        String name = "Fund A";
        BigDecimal minAmount = new BigDecimal("1000.00");
        String category = "Category A";

        Fund fund = new Fund();
        fund.setId(id);
        fund.setName(name);
        fund.setMinAmount(minAmount);
        fund.setCategory(category);

        // Act
        FundDto fundDto = fundDtoMapper.toDto(fund);

        // Assert
        assertEquals(id, fundDto.getId());
        assertEquals(name, fundDto.getName());
        assertEquals(minAmount.doubleValue(), fundDto.getMinAmount());
        assertEquals(category, fundDto.getCategory());
    }

    @Test
    public void testToDto_nullDomain() {
        // Act
        FundDto fundDto = fundDtoMapper.toDto(null);

        // Assert
        assertEquals(null, fundDto);
    }

    @Test
    public void testToDto_minAmountNull() {
        // Arrange
        Long id = 2L;
        String name = "Fund B";
        String category = "Category B";

        Fund fund = new Fund();
        fund.setId(id);
        fund.setName(name);
        fund.setMinAmount(null);
        fund.setCategory(category);

        // Act
        FundDto fundDto = fundDtoMapper.toDto(fund);

        // Assert
        assertEquals(id, fundDto.getId());
        assertEquals(name, fundDto.getName());
        assertEquals(null, fundDto.getMinAmount());
        assertEquals(category, fundDto.getCategory());
    }
}

