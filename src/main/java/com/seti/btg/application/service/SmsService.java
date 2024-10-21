package com.seti.btg.application.service;

import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final String TWILIO_NUMBER = "+18329003193";

    private final TwilioRestClient twilioClient;

    @Autowired
    public SmsService(TwilioRestClient twilioClient) {
        this.twilioClient = twilioClient;
    }

    public void sendSms(String phoneNumber, String message) {
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(TWILIO_NUMBER),
                message
        ).create(twilioClient);
    }
}
