package com.zhulidov.user_service_astondev.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhulidov.user_service_astondev.controller.UserController;
import com.zhulidov.user_service_astondev.dto.UserDto;
import com.zhulidov.user_service_astondev.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    // Тестовый DTO
    private UserDto testDto() {
        return new UserDto("Ivan", "ivan@mail.ru", 25, LocalDateTime.now());
    }

    // ==================== CREATE ====================

    @Test
    void createUser_shouldReturn201() throws Exception {
        UserDto dto = testDto();

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).saveUser(dto);
    }

    // ==================== GET BY ID ====================

    @Test
    void getUser_shouldReturnUser() throws Exception {
        UserDto dto = testDto();
        when(userService.getUserById(1L)).thenReturn(dto);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"));
    }



    // ==================== GET ALL ====================

    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        List<UserDto> users = List.of(
                testDto(),
                new UserDto("Petr", "petr@mail.ru", 30, LocalDateTime.now())
        );
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Ivan"))
                .andExpect(jsonPath("$[1].name").value("Petr"));
    }

    @Test
    void getAllUsers_empty_shouldReturnEmptyList() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/user/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ==================== UPDATE ====================

    @Test
    void updateUser_shouldReturn202() throws Exception {
        UserDto dto = testDto();

        mockMvc.perform(patch("/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted());

        verify(userService, times(1)).updateUser(dto);
    }

    // ==================== DELETE ====================

    @Test
    void deleteUser_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/user/delete/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1L);
    }
}
