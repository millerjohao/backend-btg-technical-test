package com.seti.btg.infrastructure.adapter;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import com.seti.btg.infrastructure.adapter.mapper.FundDbMapper;
import com.seti.btg.infrastructure.adapter.repository.FundRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FundJpaAdapterTest {

    @InjectMocks
    private FundJpaAdapter fundJpaAdapter;

    @Mock
    private FundRepository fundRepository;

    @Mock
    private FundDbMapper fundDbMapper;

    @Test
    void testCreateNewFund_fundAlreadyExists() {
        // Dado
        Fund fund = new Fund();
        fund.setName("Investment Fund");

        // Configuración del mock
        when(fundRepository.existsByName("Investment Fund")).thenReturn(true);

        // Cuando y Entonces
        ErrorException exception = assertThrows(ErrorException.class, () -> {
            fundJpaAdapter.createNewFund(fund);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
        assertEquals(ErrorConstant.FUND_ALREADY_EXISTS, exception.getErrorMessage());
    }

    @Test
    void testCreateNewFund_success() {
        // Dado
        Fund fund = new Fund();
        fund.setName("Investment Fund");

        FundEntity fundEntity = new FundEntity();
        fundEntity.setName("Investment Fund");

        Fund createdFund = new Fund();
        createdFund.setName("Investment Fund");

        // Configuración de mocks
        when(fundRepository.existsByName("Investment Fund")).thenReturn(false);
        when(fundDbMapper.toDbo(fund)).thenReturn(fundEntity);
        when(fundRepository.save(fundEntity)).thenReturn(fundEntity);
        when(fundDbMapper.toDomain(fundEntity)).thenReturn(createdFund);

        // Cuando
        Fund result = fundJpaAdapter.createNewFund(fund);

        // Entonces
        assertNotNull(result);
        assertEquals("Investment Fund", result.getName());
        verify(fundRepository).save(fundEntity);
    }

    @Test
    void testGetAll() {
        // Dado
        FundEntity fundEntity = new FundEntity();
        fundEntity.setName("Investment Fund");

        Fund fund = new Fund();
        fund.setName("Investment Fund");

        // Configuración del mock
        List<FundEntity> fundEntities = List.of(fundEntity);
        when(fundRepository.findAll()).thenReturn(fundEntities);
        when(fundDbMapper.toDomain(fundEntity)).thenReturn(fund);

        // Cuando
        List<Fund> result = fundJpaAdapter.getAll();

        // Entonces
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Investment Fund", result.get(0).getName());
    }

    @Test
    void testGetFundById_notFound() {
        // Dado
        Long id = 1L;

        // Configuración del mock
        when(fundRepository.findById(id)).thenReturn(Optional.empty());

        // Cuando y Entonces
        ErrorException exception = assertThrows(ErrorException.class, () -> {
            fundJpaAdapter.getFundById(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
        assertEquals(String.format(ErrorConstant.FUND_NOT_FOUND, id), exception.getErrorMessage());
    }

    @Test
    void testGetFundById_success() {
        // Dado
        Long id = 1L;
        FundEntity fundEntity = new FundEntity();
        fundEntity.setName("Investment Fund");

        Fund fund = new Fund();
        fund.setName("Investment Fund");

        // Configuración del mock
        when(fundRepository.findById(id)).thenReturn(Optional.of(fundEntity));
        when(fundDbMapper.toDomain(fundEntity)).thenReturn(fund);

        // Cuando
        Fund result = fundJpaAdapter.getFundById(id);

        // Entonces
        assertNotNull(result);
        assertEquals("Investment Fund", result.getName());
    }
}

