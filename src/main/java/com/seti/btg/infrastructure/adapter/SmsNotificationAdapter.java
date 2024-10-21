package com.seti.btg.infrastructure.adapter;

import com.seti.btg.application.service.SmsService;
import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.domain.repository.NotificationRepositoryPort;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import com.twilio.exception.TwilioException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Qualifier("smsNotification")
public class SmsNotificationAdapter implements NotificationRepositoryPort {

    private final SmsService smsService;

    @Autowired
    public SmsNotificationAdapter(SmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * Envía la notificación al número de teléfono registrado por cliente
     * @param customer
     * @param fund
     * @param transaction
     * @param message
     */
    @Override
    public void sendNotification(CustomerEntity customer, FundEntity fund, TransactionEntity transaction, String message) {
        String phoneNumber = "+57".concat(customer.getPhone());
        try {
            smsService.sendSms(phoneNumber, message);
        } catch (TwilioException e) {
            throw new ErrorException(HttpStatus.CONFLICT, ErrorConstant.NOT_VALID_NUMBER);
        }
    }
}
