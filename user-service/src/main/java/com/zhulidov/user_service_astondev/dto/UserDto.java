package com.zhulidov.user_service_astondev.dto;

import java.time.LocalDateTime;

public record UserDto(String name, String email, int age, LocalDateTime createdAt) {

}
