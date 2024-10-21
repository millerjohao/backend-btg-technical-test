package com.seti.btg.infrastructure.adapter.repository;

import com.seti.btg.infrastructure.adapter.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    boolean existsByCustomerIdAndFundId(Long customerId, Long fundId);
}
