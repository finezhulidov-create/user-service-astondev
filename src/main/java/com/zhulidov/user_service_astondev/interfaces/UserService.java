package com.zhulidov.user_service_astondev.interfaces;

import com.zhulidov.user_service_astondev.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(Long id);
}
