package com.zhulidov.user_service_astondev.service;

import com.zhulidov.user_service_astondev.config.annotations.AppComponent;
import com.zhulidov.user_service_astondev.config.annotations.Inject;
import com.zhulidov.user_service_astondev.dao.UserDAO;
import com.zhulidov.user_service_astondev.dto.UserDto;
import com.zhulidov.user_service_astondev.interfaces.UserService;
import com.zhulidov.user_service_astondev.model.User;
import com.zhulidov.user_service_astondev.util.Mapper;

import java.util.List;
@AppComponent
public class UserServiceImpl implements UserService {
   @Inject
    private  UserDAO userDAO;
   @Inject
   private Mapper mapper;

    public UserServiceImpl(UserDAO userDAO, Mapper mapper) {
        this.userDAO = userDAO;
        this.mapper = mapper;
    }

    public UserServiceImpl() {
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setAge(userDto.age());
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        userDAO.saveUser(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapper.toDto(userDAO.getUserById(id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userDAO.getAllUsers().stream().map(user -> mapper.toDto(user)).toList();
    }

    @Override
    public void updateUser(UserDto userDto) {
      User user =  mapper.toEntity(userDto);
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }
}
