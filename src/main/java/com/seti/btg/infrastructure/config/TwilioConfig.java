package com.seti.btg.infrastructure.config;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de tipo configuración para el @Component de Twilio en el ambiente
 */
@Configuration
public class TwilioConfig {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    /**
     * Componente Bean de Twilio
     * @return
     */
    @Bean
    public TwilioRestClient twilioRestClient() {
        Twilio.init(accountSid, authToken);
        return Twilio.getRestClient();
    }
}
