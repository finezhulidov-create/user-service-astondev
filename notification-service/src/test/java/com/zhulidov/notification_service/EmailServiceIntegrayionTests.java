package com.zhulidov.notification_service;

import com.zhulidov.notification_service.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;



import static org.mockito.Mockito.*;

@SpringBootTest
class EmailServiceIntegrationTests {

    @MockitoBean
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Test
    void sendCreateEmail_shouldCallMailSender() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.sendByOperation("test@yandex.ru", "CREATE");

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendDeleteEmail_shouldCallMailSender() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.sendByOperation("test@yandex.ru", "DELETE");

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendEmail_withUnknownOperation_shouldNotCallMailSender() {
        emailService.sendByOperation("test@yandex.ru", "UPDATE");

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }
}