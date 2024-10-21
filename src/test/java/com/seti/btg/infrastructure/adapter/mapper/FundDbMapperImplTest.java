package com.seti.btg.infrastructure.adapter.mapper;

import com.seti.btg.domain.model.Fund;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

public class FundDbMapperImplTest {

    private FundDbMapperImpl fundDbMapper;
    private Fund mockFund;
    private FundEntity mockFundEntity;

    @BeforeEach
    public void setUp() {
        fundDbMapper = new FundDbMapperImpl();

        mockFund = mock(Fund.class);
        mockFundEntity = mock(FundEntity.class);
    }

    @Test
    public void testToDbo_nonNullDomain() {
        // Configura el mock de Fund con valores no nulos
        Long fundId = 1L;
        when(mockFund.getId()).thenReturn(fundId);
        when(mockFund.getName()).thenReturn("Test Fund");
        when(mockFund.getMinAmount()).thenReturn(BigDecimal.valueOf(1000));
        when(mockFund.getCategory()).thenReturn("Investment");

        // Llama al método toDbo con un objeto Fund no nulo
        FundEntity fundEntity = fundDbMapper.toDbo(mockFund);

        // Verifica que el objeto FundEntity no sea null
        assertNotNull(fundEntity, "El resultado no debe ser null cuando el domain no es null");

        // Verifica que los valores se mapeen correctamente
        assertEquals(fundId, fundEntity.getId());
        assertEquals("Test Fund", fundEntity.getName());
        assertEquals(BigDecimal.valueOf(1000), fundEntity.getMinAmount());
        assertEquals("Investment", fundEntity.getCategory());
    }

    @Test
    public void testToDbo_nullDomain() {
        // Llama al método toDbo con un objeto Fund nulo
        FundEntity fundEntity = fundDbMapper.toDbo(null);

        // Verifica que el resultado sea null cuando el domain es nulo
        assertNull(fundEntity, "El resultado debe ser null cuando el domain es null");
    }

    @Test
    public void testToDomain_nonNullEntity() {
        // Configura el mock de FundEntity con valores no nulos
        Long fundId = 1L;
        when(mockFundEntity.getId()).thenReturn(fundId);
        when(mockFundEntity.getName()).thenReturn("Test Fund");
        when(mockFundEntity.getMinAmount()).thenReturn(BigDecimal.valueOf(1000));
        when(mockFundEntity.getCategory()).thenReturn("Investment");

        // Llama al método toDomain con un objeto FundEntity no nulo
        Fund fund = fundDbMapper.toDomain(mockFundEntity);

        // Verifica que el objeto Fund no sea null
        assertNotNull(fund, "El resultado no debe ser null cuando el entity no es null");

        // Verifica que los valores se mapeen correctamente
        assertEquals(fundId, fund.getId());
        assertEquals("Test Fund", fund.getName());
        assertEquals(BigDecimal.valueOf(1000), fund.getMinAmount());
        assertEquals("Investment", fund.getCategory());
    }

    @Test
    public void testToDomain_nullEntity() {
        // Llama al método toDomain con un objeto FundEntity nulo
        Fund fund = fundDbMapper.toDomain(null);

        // Verifica que el resultado sea null cuando el entity es nulo
        assertNull(fund, "El resultado debe ser null cuando el entity es null");
    }
}

