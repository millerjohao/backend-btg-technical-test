package com.seti.btg.application.service;
import com.seti.btg.application.mapper.FundDtoMapper;
import com.seti.btg.application.mapper.FundRequestMapper;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.request.FundRequest;
import com.seti.btg.domain.repository.FundRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FundServiceTest {

    @InjectMocks
    private FundService fundService;

    @Mock
    private FundRepositoryPort repository;

    @Mock
    private FundDtoMapper dtoMapper;

    @Mock
    private FundRequestMapper requestMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    public void testCreateNewFund_success() {
        // Arrange
        FundRequest fundRequest = new FundRequest();
        fundRequest.setName("Tech Fund");
        fundRequest.setMinAmount(BigDecimal.valueOf(50000.00));
        fundRequest.setCategory("Technology");

        Fund newFund = new Fund();
        newFund.setName("Tech Fund");
        newFund.setMinAmount(BigDecimal.valueOf(50000.00));
        newFund.setCategory("Technology");

        Fund createdFund = new Fund();
        createdFund.setId(1L);
        createdFund.setName("Tech Fund");
        createdFund.setMinAmount(BigDecimal.valueOf(50000.00));
        createdFund.setCategory("Technology");

        FundDto fundDto = new FundDto();
        fundDto.setId(1L);
        fundDto.setName("Tech Fund");
        fundDto.setMinAmount(50000.00);  // Cambié el tipo a Double para coincidir con el DTO
        fundDto.setCategory("Technology");

        // Simulamos los métodos de los mocks
        when(requestMapper.toDomain(fundRequest)).thenReturn(newFund);
        when(repository.createNewFund(newFund)).thenReturn(createdFund);
        when(dtoMapper.toDto(createdFund)).thenReturn(fundDto);

        // Act
        FundDto result = fundService.createNewFund(fundRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Tech Fund", result.getName());
        assertEquals(50000.00, result.getMinAmount());
        assertEquals("Technology", result.getCategory());
    }

    @Test
    public void testGetAll_success() {
        // Arrange
        Fund fund1 = new Fund();
        fund1.setId(1L);
        fund1.setName("Tech Fund");
        fund1.setMinAmount(BigDecimal.valueOf(50000.00));
        fund1.setCategory("Technology");

        Fund fund2 = new Fund();
        fund2.setId(2L);
        fund2.setName("Health Fund");
        fund2.setMinAmount(BigDecimal.valueOf(100000.00));
        fund2.setCategory("Health");

        List<Fund> fundList = Arrays.asList(fund1, fund2);

        FundDto fundDto1 = new FundDto();
        fundDto1.setId(1L);
        fundDto1.setName("Tech Fund");
        fundDto1.setMinAmount(50000.00);  // Cambié el tipo a Double para coincidir con el DTO
        fundDto1.setCategory("Technology");

        FundDto fundDto2 = new FundDto();
        fundDto2.setId(2L);
        fundDto2.setName("Health Fund");
        fundDto2.setMinAmount(100000.00);  // Cambié el tipo a Double para coincidir con el DTO
        fundDto2.setCategory("Health");

        List<FundDto> fundDtoList = Arrays.asList(fundDto1, fundDto2);

        // Simulamos los métodos de los mocks
        when(repository.getAll()).thenReturn(fundList);
        when(dtoMapper.toDto(fund1)).thenReturn(fundDto1);
        when(dtoMapper.toDto(fund2)).thenReturn(fundDto2);

        // Act
        List<FundDto> result = fundService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Tech Fund", result.get(0).getName());
        assertEquals("Health Fund", result.get(1).getName());
        assertEquals(50000.00, result.get(0).getMinAmount());
        assertEquals(100000.00, result.get(1).getMinAmount());
    }

    @Test
    public void testGetFundById_success() {
        // Arrange
        Long fundId = 1L;
        Fund fund = new Fund();
        fund.setId(1L);
        fund.setName("Tech Fund");
        fund.setMinAmount(BigDecimal.valueOf(50000.00));
        fund.setCategory("Technology");

        FundDto fundDto = new FundDto();
        fundDto.setId(1L);
        fundDto.setName("Tech Fund");
        fundDto.setMinAmount(50000.00);  // Cambié el tipo a Double para coincidir con el DTO
        fundDto.setCategory("Technology");

        // Simulamos los métodos de los mocks
        when(repository.getFundById(fundId)).thenReturn(fund);
        when(dtoMapper.toDto(fund)).thenReturn(fundDto);

        // Act
        FundDto result = fundService.getFundById(fundId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Tech Fund", result.getName());
        assertEquals(50000.00, result.getMinAmount());
        assertEquals("Technology", result.getCategory());
    }
}
