package com.zhulidov.user_service_astondev.service;


import com.zhulidov.user_service_astondev.dao.UserRepository;
import com.zhulidov.user_service_astondev.dto.UserDto;
import com.zhulidov.user_service_astondev.interfaces.UserService;
import com.zhulidov.user_service_astondev.model.User;
import com.zhulidov.user_service_astondev.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
   private Mapper mapper;

    public UserServiceImpl(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
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
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapper.toDto(userRepository.findById(id).orElseThrow());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> mapper.toDto(user)).toList();
    }

    @Override
    public void updateUser(UserDto userDto) {
      User user =  mapper.toEntity(userDto);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow());
    }
}
