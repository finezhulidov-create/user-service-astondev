package com.zhulidov.user_service_astondev.dao;

import com.zhulidov.user_service_astondev.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserDAOTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.2"))
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        // Настройка Hibernate с использованием контейнера
        System.setProperty("hibernate.connection.url", postgresContainer.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgresContainer.getUsername());
        System.setProperty("hibernate.connection.password", postgresContainer.getPassword());
        
        userDAO = new UserDAO();
    }

    @Test
    void testSaveUser() {
        User user = new User("John Doe", "john@example.com", 30);
        user.setCreatedAt(LocalDateTime.now());
        
        userDAO.saveUser(user);
        
        User savedUser = userDAO.getUserById(user.getId());
        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john@example.com", savedUser.getEmail());
        assertEquals(30, savedUser.getAge());
    }

    @Test
    void testGetUserById() {
        User user = new User("Jane Doe", "jane@example.com", 25);
        user.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(user);
        
        User foundUser = userDAO.getUserById(user.getId());
        
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals("Jane Doe", foundUser.getName());
    }

    @Test
    void testGetUserByIdNotFound() {
        User user = userDAO.getUserById(999L);
        assertNull(user);
    }

    @Test
    void testGetAllUsers() {
        // Очистка перед тестом
        
        User user1 = new User("User One", "one@example.com", 20);
        user1.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(user1);
        
        User user2 = new User("User Two", "two@example.com", 25);
        user2.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(user2);
        
        List<User> users = userDAO.getAllUsers();
        
        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("User One")));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("User Two")));
    }

    @Test
    void testUpdateUser() {
        User user = new User("Original Name", "original@example.com", 30);
        user.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(user);
        
        // Обновляем данные
        user.setName("Updated Name");
        user.setEmail("updated@example.com");
        user.setAge(35);
        
        userDAO.updateUser(user);
        
        User updatedUser = userDAO.getUserById(user.getId());
        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
        assertEquals(35, updatedUser.getAge());
    }

    @Test
    void testDeleteUser() {
        User user = new User("ToDelete", "delete@example.com", 40);
        user.setCreatedAt(LocalDateTime.now());
        userDAO.saveUser(user);
        
        Long userId = user.getId();
        assertNotNull(userDAO.getUserById(userId));
        
        userDAO.deleteUser(userId);
        
        assertNull(userDAO.getUserById(userId));
    }

    @Test
    void testDeleteUserNotFound() {
        // Удаление несуществующего пользователя не должно вызывать ошибок
        assertDoesNotThrow(() -> userDAO.deleteUser(999L));
    }
}