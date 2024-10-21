package com.seti.btg.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail_success() {
        // Arrange
        String to = "test@example.com";
        String subject = "Test Subject";
        String body = "This is a test email.";

        // Act
        emailService.sendEmail(to, subject, body);

        // Assert
        verify(mailSender, times(1)).send(argThat((SimpleMailMessage message) -> {
            return message.getTo()[0].equals(to) && message.getSubject().equals(subject) && message.getText().equals(body);
        }));
    }
}

