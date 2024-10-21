package com.seti.btg.application.mapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.seti.btg.domain.model.Subscription;
import com.seti.btg.domain.model.dto.SubscriptionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SubscriptionDtoMapperImplTest {

    private SubscriptionDtoMapperImpl subscriptionDtoMapper;

    @BeforeEach
    public void setUp() {
        subscriptionDtoMapper = new SubscriptionDtoMapperImpl();
    }

    @Test
    public void testToDto_success() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setIdCustomer(1L);
        subscription.setIdFund(2L);

        // Act
        SubscriptionDto subscriptionDto = subscriptionDtoMapper.toDto(subscription);

        // Assert
        assertEquals(1L, subscriptionDto.getIdCustomer());
        assertEquals(2L, subscriptionDto.getIdFund());
    }

    @Test
    public void testToDto_null() {
        // Act
        SubscriptionDto subscriptionDto = subscriptionDtoMapper.toDto(null);

        // Assert
        assertEquals(null, subscriptionDto);
    }
}

