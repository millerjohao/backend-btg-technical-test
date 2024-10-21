package com.seti.btg.application.port;

import com.seti.btg.domain.model.dto.SubscriptionDto;
import com.seti.btg.domain.model.request.SubscriptionRequest;

public interface SubscriptionPort {
    SubscriptionDto createSubscription(SubscriptionRequest subscription);
}
