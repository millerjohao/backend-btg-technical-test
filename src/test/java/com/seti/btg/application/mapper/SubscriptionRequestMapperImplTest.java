package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Subscription;
import com.seti.btg.domain.model.request.SubscriptionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SubscriptionRequestMapperImplTest {
    private SubscriptionRequestMapperImpl subscriptionRequestMapper;

    @BeforeEach
    public void setUp() {
        subscriptionRequestMapper = new SubscriptionRequestMapperImpl();
    }

    @Test
    public void testToDomain_success() {
        // Arrange
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
        subscriptionRequest.setIdCustomer(1L);
        subscriptionRequest.setIdFund(2L);

        // Act
        Subscription subscription = subscriptionRequestMapper.toDomain(subscriptionRequest);

        // Assert
        assertEquals(1L, subscription.getIdCustomer());
        assertEquals(2L, subscription.getIdFund());
    }

    @Test
    public void testToDomain_null() {
        // Act
        Subscription subscription = subscriptionRequestMapper.toDomain(null);

        // Assert
        assertEquals(null, subscription);
    }
}
