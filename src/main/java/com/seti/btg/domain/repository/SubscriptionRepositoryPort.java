package com.seti.btg.domain.repository;

import com.seti.btg.domain.model.Subscription;

public interface SubscriptionRepositoryPort {
    Subscription createSubscription(Subscription fund);
}
