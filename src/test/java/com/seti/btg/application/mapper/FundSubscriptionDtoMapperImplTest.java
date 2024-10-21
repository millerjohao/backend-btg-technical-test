package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.FundSubscription;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.dto.FundSubscriptionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class FundSubscriptionDtoMapperImplTest {

    private FundSubscriptionDtoMapperImpl mapper;

    @BeforeEach
    public void setUp() {
        mapper = new FundSubscriptionDtoMapperImpl();
    }

    @Test
    public void testToDto_NullInput() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ValidInput() {
        Fund fund = new Fund();
        fund.setId(1L);
        fund.setName("Pension Fund");
        fund.setMinAmount(BigDecimal.valueOf(1000.00));
        fund.setCategory("Retirement");

        FundSubscription fundSubscription = new FundSubscription();
        fundSubscription.setFund(fund);
        fundSubscription.setAmount(BigDecimal.valueOf(2000.00));

        FundSubscriptionDto dto = mapper.toDto(fundSubscription);

        assertNotNull(dto);
        assertEquals(1L, dto.getFundDto().getId());
        assertEquals("Pension Fund", dto.getFundDto().getName());
        assertEquals(1000.00, dto.getFundDto().getMinAmount());
        assertEquals("Retirement", dto.getFundDto().getCategory());
        assertEquals(BigDecimal.valueOf(2000.00), dto.getAmount());
    }

    @Test
    public void testToDomain_NullInput() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    public void testToDomain_ValidInput() {
        FundDto fundDto = new FundDto();
        fundDto.setId(1L);
        fundDto.setName("Pension Fund");
        fundDto.setMinAmount(1000.00);
        fundDto.setCategory("Retirement");

        FundSubscriptionDto dto = new FundSubscriptionDto();
        dto.setFundDto(fundDto);
        dto.setAmount(BigDecimal.valueOf(2000.00));

        FundSubscription fundSubscription = mapper.toDomain(dto);

        assertNotNull(fundSubscription);
        assertEquals(1L, fundSubscription.getFund().getId());
        assertEquals("Pension Fund", fundSubscription.getFund().getName());
        assertEquals(BigDecimal.valueOf(1000.00), fundSubscription.getFund().getMinAmount());
        assertEquals("Retirement", fundSubscription.getFund().getCategory());
        assertEquals(BigDecimal.valueOf(2000.00), fundSubscription.getAmount());
    }

    @Test
    public void testFundToFundDto_NullInput() {
        assertNull(mapper.fundToFundDto(null));
    }

    @Test
    public void testFundToFundDto_ValidInput() {
        Fund fund = new Fund();
        fund.setId(1L);
        fund.setName("Pension Fund");
        fund.setMinAmount(BigDecimal.valueOf(1000.00));
        fund.setCategory("Retirement");

        FundDto fundDto = mapper.fundToFundDto(fund);

        assertNotNull(fundDto);
        assertEquals(1L, fundDto.getId());
        assertEquals("Pension Fund", fundDto.getName());
        assertEquals(1000.00, fundDto.getMinAmount());
        assertEquals("Retirement", fundDto.getCategory());
    }

    @Test
    public void testFundDtoToFund_NullInput() {
        assertNull(mapper.fundDtoToFund(null));
    }

    @Test
    public void testFundDtoToFund_ValidInput() {
        FundDto fundDto = new FundDto();
        fundDto.setId(1L);
        fundDto.setName("Pension Fund");
        fundDto.setMinAmount(1000.00);
        fundDto.setCategory("Retirement");

        Fund fund = mapper.fundDtoToFund(fundDto);

        assertNotNull(fund);
        assertEquals(1L, fund.getId());
        assertEquals("Pension Fund", fund.getName());
        assertEquals(BigDecimal.valueOf(1000.00), fund.getMinAmount());
        assertEquals("Retirement", fund.getCategory());
    }
}

