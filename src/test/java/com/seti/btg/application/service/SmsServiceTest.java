package com.seti.btg.application.service;

import com.twilio.rest.api.v2010.account.MessageCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SmsServiceTest {

    @Mock
    private com.twilio.http.TwilioRestClient twilioClient;

    @Mock
    private MessageCreator messageCreator;

    private SmsService smsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        smsService = new SmsService(twilioClient);
    }
}

