package com.zhulidov.user_service_astondev.dto;

import java.time.LocalDateTime;

public record UserDto(Long id, String name, String email, int age, LocalDateTime createdAt) {

}
