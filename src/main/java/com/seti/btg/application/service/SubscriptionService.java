package com.seti.btg.application.service;

import com.seti.btg.application.mapper.SubscriptionDtoMapper;
import com.seti.btg.application.mapper.SubscriptionRequestMapper;
import com.seti.btg.application.port.SubscriptionPort;
import com.seti.btg.domain.model.dto.SubscriptionDto;
import com.seti.btg.domain.model.request.SubscriptionRequest;
import com.seti.btg.domain.repository.SubscriptionRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService implements SubscriptionPort {

    @Autowired
    SubscriptionDtoMapper dtoMapper;
    @Autowired
    private SubscriptionRepositoryPort repository;
    @Autowired
    private SubscriptionRequestMapper requestMapper;

    @Override
    public SubscriptionDto createSubscription(SubscriptionRequest subscription) {
        var newSubscription = requestMapper.toDomain(subscription);
        var createdSub = repository.createSubscription(newSubscription);
        return dtoMapper.toDto(createdSub);
    }
}
