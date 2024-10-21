package com.seti.btg.domain.repository;

import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;

public interface NotificationRepositoryPort {
    void sendNotification(CustomerEntity customer, FundEntity fund, TransactionEntity transaction, String message);
}
