package com.zhulidov.user_service_astondev.util;

import com.zhulidov.user_service_astondev.dto.UserDto;
import com.zhulidov.user_service_astondev.model.User;

import java.time.LocalDateTime;

public class Mapper {
    public UserDto toDto(User user){
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getCreatedAt());
    }
    public User toEntity(UserDto userDto){
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setAge(userDto.age());
        user.setCreatedAt(userDto.createdAt());
        return user;
    }
}
