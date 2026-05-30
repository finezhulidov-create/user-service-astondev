package com.zhulidov.notification_service.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender sender;

    public EmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }


    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        sender.send(message);
    }

    @Override
    public void sendByOperation(String email, String operation) {
        if ("CREATE".equalsIgnoreCase(operation)){
            sendEmail(email, "Добро пожаловать!", "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
        } else if ("DELETE".equalsIgnoreCase(operation)) {
            sendEmail(email, "Пользователь удален", "Здравствуйте! Ваш аккаунт был удалён");

        }
    }
}
