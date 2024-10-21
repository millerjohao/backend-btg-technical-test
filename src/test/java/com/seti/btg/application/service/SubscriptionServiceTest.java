package com.seti.btg.application.service;

import com.seti.btg.application.mapper.SubscriptionDtoMapper;
import com.seti.btg.application.mapper.SubscriptionRequestMapper;
import com.seti.btg.domain.model.Subscription;
import com.seti.btg.domain.model.dto.SubscriptionDto;
import com.seti.btg.domain.model.request.SubscriptionRequest;
import com.seti.btg.domain.repository.SubscriptionRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionDtoMapper dtoMapper;

    @Mock
    private SubscriptionRepositoryPort repository;

    @Mock
    private SubscriptionRequestMapper requestMapper;

    @Mock
    private SubscriptionRequest subscriptionRequest;

    @Mock
    private Subscription subscription;

    @Mock
    private SubscriptionDto subscriptionDto;

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks antes de cada test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSubscription() {
        // Arrange
        when(requestMapper.toDomain(subscriptionRequest)).thenReturn(subscription);
        when(repository.createSubscription(subscription)).thenReturn(subscription);
        when(dtoMapper.toDto(subscription)).thenReturn(subscriptionDto);

        // Act
        SubscriptionDto result = subscriptionService.createSubscription(subscriptionRequest);

        // Assert
        assertNotNull(result);
        assertEquals(subscriptionDto, result);
        verify(requestMapper, times(1)).toDomain(subscriptionRequest);
        verify(repository, times(1)).createSubscription(subscription);
        verify(dtoMapper, times(1)).toDto(subscription);
    }
}
