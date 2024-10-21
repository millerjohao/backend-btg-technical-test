package com.seti.btg.infrastructure.adapter;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.FundSubscription;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.dto.FundSubscriptionDto;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import com.seti.btg.infrastructure.adapter.mapper.FundDbMapper;
import com.seti.btg.infrastructure.adapter.mapper.FundSubscriptionDbMapper;
import com.seti.btg.infrastructure.adapter.repository.FundRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FundJpaAdapterTest {

    @InjectMocks
    private FundJpaAdapter fundJpaAdapter;

    @Mock
    private FundRepository fundRepository;

    @Mock
    private FundDbMapper fundDbMapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private FundSubscriptionDbMapper fundSubscriptionDbMapper;

    @Mock
    private TypedQuery<Object[]> typedQuery;

    @Test
    void testCreateNewFund_fundAlreadyExists() {

        Fund fund = new Fund();
        fund.setName("Investment Fund");


        when(fundRepository.existsByName("Investment Fund")).thenReturn(true);


        ErrorException exception = assertThrows(ErrorException.class, () -> {
            fundJpaAdapter.createNewFund(fund);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
        assertEquals(ErrorConstant.FUND_ALREADY_EXISTS, exception.getErrorMessage());
    }

    @Test
    void testCreateNewFund_success() {

        Fund fund = new Fund();
        fund.setName("Investment Fund");

        FundEntity fundEntity = new FundEntity();
        fundEntity.setName("Investment Fund");

        Fund createdFund = new Fund();
        createdFund.setName("Investment Fund");


        when(fundRepository.existsByName("Investment Fund")).thenReturn(false);
        when(fundDbMapper.toDbo(fund)).thenReturn(fundEntity);
        when(fundRepository.save(fundEntity)).thenReturn(fundEntity);
        when(fundDbMapper.toDomain(fundEntity)).thenReturn(createdFund);


        Fund result = fundJpaAdapter.createNewFund(fund);


        assertNotNull(result);
        assertEquals("Investment Fund", result.getName());
        verify(fundRepository).save(fundEntity);
    }

    @Test
    void testGetAll() {

        FundEntity fundEntity = new FundEntity();
        fundEntity.setName("Investment Fund");

        Fund fund = new Fund();
        fund.setName("Investment Fund");


        List<FundEntity> fundEntities = List.of(fundEntity);
        when(fundRepository.findAll()).thenReturn(fundEntities);
        when(fundDbMapper.toDomain(fundEntity)).thenReturn(fund);

        List<Fund> result = fundJpaAdapter.getAll();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Investment Fund", result.get(0).getName());
    }

    @Test
    void testGetFundById_notFound() {

        Long id = 1L;


        when(fundRepository.findById(id)).thenReturn(Optional.empty());

        ErrorException exception = assertThrows(ErrorException.class, () -> {
            fundJpaAdapter.getFundById(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
        assertEquals(String.format(ErrorConstant.FUND_NOT_FOUND, id), exception.getErrorMessage());
    }

    @Test
    void testGetFundById_success() {

        Long id = 1L;
        FundEntity fundEntity = new FundEntity();
        fundEntity.setName("Investment Fund");

        Fund fund = new Fund();
        fund.setName("Investment Fund");


        when(fundRepository.findById(id)).thenReturn(Optional.of(fundEntity));
        when(fundDbMapper.toDomain(fundEntity)).thenReturn(fund);


        Fund result = fundJpaAdapter.getFundById(id);

        assertNotNull(result);
        assertEquals("Investment Fund", result.getName());
    }

    @Test
    void testGetFundsByCustomerId() {
        // Arrange
        Long customerId = 1L;
        FundEntity fund = new FundEntity(1L, "fondo1", BigDecimal.valueOf(40000.00), "FIC", null, null);
        Object[] queryResult = new Object[]{fund, BigDecimal.valueOf(500)};
        List<Object[]> resultList = Arrays.asList(new Object[][]{queryResult});

        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(eq("customerId"), eq(customerId))).thenReturn(query);
        when(query.getResultList()).thenReturn(resultList);

        FundDto fundDto = new FundDto(1L, "Test Fund", 100.0, "Category");
        FundSubscriptionDto fundSubscriptionDto = new FundSubscriptionDto(fundDto, BigDecimal.valueOf(500));
        FundSubscription expectedFundSubscription = new FundSubscription(); // Assume this is the expected result

        when(fundSubscriptionDbMapper.toEntity(any(FundSubscriptionDto.class))).thenReturn(expectedFundSubscription);

        // Act
        List<FundSubscription> result = fundJpaAdapter.getFundsByCustomerId(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedFundSubscription, result.get(0));

        verify(entityManager).createQuery(anyString());
        verify(query).setParameter("customerId", customerId);
        verify(query).getResultList();
        verify(fundSubscriptionDbMapper).toEntity(any(FundSubscriptionDto.class));
    }
}

