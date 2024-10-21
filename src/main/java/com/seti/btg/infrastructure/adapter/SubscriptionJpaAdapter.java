package com.seti.btg.infrastructure.adapter;

import com.seti.btg.domain.model.Subscription;
import com.seti.btg.domain.repository.SubscriptionRepositoryPort;
import com.seti.btg.infrastructure.adapter.mapper.SubscriptionDbMapper;
import com.seti.btg.infrastructure.adapter.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SubscriptionJpaAdapter implements SubscriptionRepositoryPort {
    @Autowired
    private SubscriptionRepository repository;
    @Autowired
    private SubscriptionDbMapper dbMapper;

    @Override
    public Subscription createSubscription(Subscription subscription) {
        return null;
    }
}

