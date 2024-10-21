package com.seti.btg.infrastructure.adapter.mapper;

import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.FundSubscription;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.dto.FundSubscriptionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FundSubscriptionDbMapperImplTest {

    private FundSubscriptionDbMapperImpl mapper;

    @BeforeEach
    public void setUp() {
        mapper = new FundSubscriptionDbMapperImpl();
    }


    @Test
    public void testToEntity_NullInput() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    public void testToEntity_ValidInput() {
        FundDto fundDto = new FundDto();
        fundDto.setId(1L);
        fundDto.setName("Pension Fund");
        fundDto.setMinAmount(1000.00);
        fundDto.setCategory("Retirement");

        FundSubscriptionDto dto = new FundSubscriptionDto();
        dto.setFundDto(fundDto);
        dto.setAmount(BigDecimal.valueOf(2000.00));

        FundSubscription fundSubscription = mapper.toEntity(dto);

        assertNotNull(fundSubscription);
        assertEquals(1L, fundSubscription.getFund().getId());
        assertEquals("Pension Fund", fundSubscription.getFund().getName());
        assertEquals(BigDecimal.valueOf(1000.00), fundSubscription.getFund().getMinAmount());
        assertEquals("Retirement", fundSubscription.getFund().getCategory());
        assertEquals(BigDecimal.valueOf(2000.00), fundSubscription.getAmount());
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
    public void testToEntityList_NullInput() {
        assertNull(mapper.toEntityList(null));
    }

    @Test
    public void testToEntityList_ValidInput() {
        List<FundSubscriptionDto> dtoList = new ArrayList<>();
        FundDto fundDto = new FundDto();
        fundDto.setId(1L);
        fundDto.setName("Pension Fund");
        fundDto.setMinAmount(1000.00);
        fundDto.setCategory("Retirement");

        FundSubscriptionDto dto = new FundSubscriptionDto();
        dto.setFundDto(fundDto);
        dto.setAmount(BigDecimal.valueOf(2000.00));
        dtoList.add(dto);

        List<FundSubscription> fundSubscriptionList = mapper.toEntityList(dtoList);

        assertNotNull(fundSubscriptionList);
        assertEquals(1, fundSubscriptionList.size());
        assertEquals(1L, fundSubscriptionList.get(0).getFund().getId());
        assertEquals("Pension Fund", fundSubscriptionList.get(0).getFund().getName());
        assertEquals(BigDecimal.valueOf(2000.00), fundSubscriptionList.get(0).getAmount());
    }

    @Test
    public void testToDtoList_NullInput() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    public void testToDtoList_ValidInput() {
        List<FundSubscription> entityList = new ArrayList<>();
        Fund fund = new Fund();
        fund.setId(1L);
        fund.setName("Pension Fund");
        fund.setMinAmount(BigDecimal.valueOf(1000.00));
        fund.setCategory("Retirement");

        FundSubscription fundSubscription = new FundSubscription();
        fundSubscription.setFund(fund);
        fundSubscription.setAmount(BigDecimal.valueOf(2000.00));
        entityList.add(fundSubscription);

        List<FundSubscriptionDto> dtoList = mapper.toDtoList(entityList);

        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());
        assertEquals(1L, dtoList.get(0).getFundDto().getId());
        assertEquals("Pension Fund", dtoList.get(0).getFundDto().getName());
        assertEquals(1000.00, dtoList.get(0).getFundDto().getMinAmount());
        assertEquals("Retirement", dtoList.get(0).getFundDto().getCategory());
        assertEquals(BigDecimal.valueOf(2000.00), dtoList.get(0).getAmount());
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
}