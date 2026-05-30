package com.zhulidov.notification_service;

import com.zhulidov.notification_service.dto.UserEvent;
import com.zhulidov.notification_service.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "user-events")
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer"
})
class UserEventConsumerIntegrationTest {

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @MockitoBean
    private EmailService emailService;

    @Test
    void whenCreateEventSent_shouldCallEmailService() throws InterruptedException {
        kafkaTemplate.send("user-events", new UserEvent("test@yandex.ru", "CREATE"));

        Thread.sleep(2000);

        verify(emailService, times(1))
                .sendByOperation("test@yandex.ru", "CREATE");
    }

    @Test
    void whenDeleteEventSent_shouldCallEmailService() throws InterruptedException {
        kafkaTemplate.send("user-events", new UserEvent("test@yandex.ru", "DELETE"));

        Thread.sleep(2000);

        verify(emailService, times(1))
                .sendByOperation("test@yandex.ru", "DELETE");
    }
}