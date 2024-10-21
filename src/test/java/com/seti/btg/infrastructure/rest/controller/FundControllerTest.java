package com.seti.btg.infrastructure.rest.controller;

import com.seti.btg.application.service.FundService;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.request.FundRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FundControllerTest {

    @InjectMocks
    private FundController fundController;

    @Mock
    private FundService fundService;

    @Mock
    private FundDto fundDto;

    @Mock
    private FundRequest fundRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFunds() {
        // Arrange
        List<FundDto> funds = Arrays.asList(fundDto);
        when(fundService.getAll()).thenReturn(funds);

        // Act
        List<FundDto> result = fundController.getAllFunds();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(fundDto, result.get(0));
        verify(fundService, times(1)).getAll();
    }

    @Test
    public void testCreateFund() {
        // Arrange
        when(fundService.createNewFund(any(FundRequest.class))).thenReturn(fundDto);

        // Act
        FundDto result = fundController.createFund(fundRequest);

        // Assert
        assertNotNull(result);
        assertEquals(fundDto, result);
        verify(fundService, times(1)).createNewFund(fundRequest);
    }

    @Test
    public void testGetFundById() {
        // Arrange
        long fundId = 1L;
        when(fundService.getFundById(fundId)).thenReturn(fundDto);

        // Act
        FundDto result = fundController.getFundById(fundId);

        // Assert
        assertNotNull(result);
        assertEquals(fundDto, result);
        verify(fundService, times(1)).getFundById(fundId);
    }
}
