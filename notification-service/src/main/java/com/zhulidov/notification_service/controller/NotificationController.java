package com.zhulidov.notification_service.controller;


import com.zhulidov.notification_service.dto.UserEvent;
import com.zhulidov.notification_service.events.UserEventConsumer;
import com.zhulidov.notification_service.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
public class NotificationController {


    private final EmailService service;

    public NotificationController(EmailService service) {
        this.service = service;

    }

    @PostMapping
    public ResponseEntity<Void> createEvent(@RequestBody UserEvent event) {
        service.sendByOperation(event.email(), event.operation());
        return ResponseEntity.ok().build();
    }

}
