package com.seti.btg.infrastructure.adapter;

import com.seti.btg.infrastructure.adapter.mapper.SubscriptionDbMapper;
import com.seti.btg.infrastructure.adapter.repository.SubscriptionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriptionJpaAdapterTest {
    @InjectMocks
    private SubscriptionJpaAdapter subscriptionJpaAdapter;

    @Mock
    private SubscriptionRepository repository;

    @Mock
    private SubscriptionDbMapper dbMapper;


}

