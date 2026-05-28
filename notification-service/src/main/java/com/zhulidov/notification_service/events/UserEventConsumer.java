package com.zhulidov.notification_service.events;

import com.zhulidov.notification_service.dto.UserEvent;
import com.zhulidov.notification_service.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventConsumer {



    private final EmailService emailService;

    public UserEventConsumer( EmailService emailService) {
        this.emailService = emailService;
    }
    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consume(UserEvent event){
        emailService.sendByOperation(event.email(),event.operation());
    }
}
