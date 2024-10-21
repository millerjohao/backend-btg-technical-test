package com.seti.btg.infrastructure.adapter;

import com.seti.btg.application.service.EmailService;
import com.seti.btg.domain.constant.ResponseConstant;
import com.seti.btg.domain.repository.NotificationRepositoryPort;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Qualifier("emailNotification")
public class EmailNotificationAdapter implements NotificationRepositoryPort {

    private final EmailService emailService;

    public EmailNotificationAdapter(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Envía la notificación al email registrado por el cliente
     * @param customer
     * @param fund
     * @param transaction
     * @param message
     */
    @Override
    public void sendNotification(CustomerEntity customer, FundEntity fund, TransactionEntity transaction, String message) {
        String subject = ResponseConstant.SUBSCRIPTION_TITLE + fund.getName();
        String body = String.format(ResponseConstant.SUBSCRIPTION_BODY,
                customer.getName(), fund.getName(), transaction.getAmount());
        emailService.sendEmail(customer.getEmail(), subject, body);
    }
}
