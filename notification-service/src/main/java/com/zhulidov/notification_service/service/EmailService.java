package com.zhulidov.notification_service.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
    void sendByOperation(String email, String operation);
}
