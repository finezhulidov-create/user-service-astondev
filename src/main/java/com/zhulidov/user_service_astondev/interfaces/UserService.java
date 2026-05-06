package com.zhulidov.user_service_astondev.interfaces;

import com.zhulidov.user_service_astondev.dto.UserDto;
import com.zhulidov.user_service_astondev.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto user);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    void updateUser(UserDto user);
    void deleteUser(Long id);
}
