package com.zhulidov.user_service_astondev.service;

import com.zhulidov.user_service_astondev.config.AppComponent;
import com.zhulidov.user_service_astondev.config.Inject;
import com.zhulidov.user_service_astondev.dao.UserDAO;
import com.zhulidov.user_service_astondev.interfaces.UserService;
import com.zhulidov.user_service_astondev.model.User;

import java.util.List;
@AppComponent
public class UserServiceImpl implements UserService {
   @Inject
    private  UserDAO userDAO;

    public UserServiceImpl() {
    }

    @Override
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }
}
