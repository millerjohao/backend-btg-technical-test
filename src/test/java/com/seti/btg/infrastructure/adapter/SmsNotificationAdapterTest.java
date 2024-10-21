package com.seti.btg.infrastructure.adapter;

import com.seti.btg.application.service.SmsService;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SmsNotificationAdapterTest {

    @InjectMocks
    private SmsNotificationAdapter smsNotificationAdapter;

    @Mock
    private SmsService smsService;

    @Test
    void testSendNotification_success() {

        CustomerEntity customer = new CustomerEntity();
        customer.setPhone("1234567890");

        FundEntity fund = new FundEntity();
        TransactionEntity transaction = new TransactionEntity();

        String message = "Test Message";


        doNothing().when(smsService).sendSms(anyString(), anyString());


        smsNotificationAdapter.sendNotification(customer, fund, transaction, message);


        verify(smsService).sendSms(eq("+571234567890"), eq(message));
    }
}

