package com.zhulidov.user_service_astondev.dao.interfaces;

import com.zhulidov.user_service_astondev.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(Long id);
}
