package com.zhulidov.user_service_astondev.events;

import com.zhulidov.user_service_astondev.dto.UserEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;
    private static final String TOPIC = "user-events";

    public UserEventProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String email, String operation){
        kafkaTemplate.send(TOPIC, new UserEvent(email,operation));
    }
}
