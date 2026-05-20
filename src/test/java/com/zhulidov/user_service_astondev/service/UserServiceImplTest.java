package com.zhulidov.user_service_astondev.service;

import com.zhulidov.user_service_astondev.dao.UserDAO;
import com.zhulidov.user_service_astondev.dto.UserDto;
import com.zhulidov.user_service_astondev.model.User;
import com.zhulidov.user_service_astondev.util.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;
    
    @Mock
    private Mapper mapper;
    
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", "john@example.com", 30);
        user.setId(1L);
        user.setCreatedAt(LocalDateTime.now());
        
        userDto = new UserDto(1L, "John Doe", "john@example.com", 30, user.getCreatedAt());
    }

    @Test
    void testSaveUser() {
        // Act
        userService.saveUser(userDto);
        
        // Assert
        verify(userDAO).saveUser(argThat(user -> {
            return user.getName().equals("John Doe") &&
                   user.getEmail().equals("john@example.com") &&
                   user.getAge() == 30;
        }));
    }

    @Test
    void testGetUserByIdFound() {
        // Arrange
        when(userDAO.getUserById(1L)).thenReturn(user);
        when(mapper.toDto(user)).thenReturn(userDto);
        
        // Act
        UserDto result = userService.getUserById(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals(userDto.id(), result.id());
        assertEquals(userDto.name(), result.name());
        verify(userDAO).getUserById(1L);
        verify(mapper).toDto(user);
    }

    @Test
    void testGetUserByIdNotFound() {
        // Arrange
        when(userDAO.getUserById(999L)).thenReturn(null);
        
        // Act
        UserDto result = userService.getUserById(999L);
        
        // Assert
        assertNull(result);
        verify(userDAO).getUserById(999L);
        verify(mapper, never()).toDto(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        User user2 = new User("Jane Doe", "jane@example.com", 25);
        user2.setId(2L);
        user2.setCreatedAt(LocalDateTime.now());
        
        UserDto userDto2 = new UserDto(2L, "Jane Doe", "jane@example.com", 25, user2.getCreatedAt());
        
        when(userDAO.getAllUsers()).thenReturn(Arrays.asList(user, user2));
        when(mapper.toDto(user)).thenReturn(userDto);
        when(mapper.toDto(user2)).thenReturn(userDto2);
        
        // Act
        List<UserDto> result = userService.getAllUsers();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userDto.id(), result.get(0).id());
        assertEquals(userDto2.id(), result.get(1).id());
        verify(userDAO).getAllUsers();
        verify(mapper, times(2)).toDto(any(User.class));
    }

    @Test
    void testUpdateUser() {
        // Arrange
        when(mapper.toEntity(userDto)).thenReturn(user);
        
        // Act
        userService.updateUser(userDto);
        
        // Assert
        verify(mapper).toEntity(userDto);
        verify(userDAO).updateUser(user);
    }

    @Test
    void testDeleteUser() {
        // Act
        userService.deleteUser(1L);
        
        // Assert
        verify(userDAO).deleteUser(1L);
    }
}