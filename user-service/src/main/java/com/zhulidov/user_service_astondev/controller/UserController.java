package com.zhulidov.user_service_astondev.controller;

import com.zhulidov.user_service_astondev.dto.UserDto;
import com.zhulidov.user_service_astondev.events.UserEventProducer;
import com.zhulidov.user_service_astondev.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserEventProducer producer;
    private final UserService userService;

    public UserController(UserEventProducer producer, UserService userService) {
        this.producer = producer;
        this.userService = userService;
    }


    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){
        producer.sendEvent(dto.email(), "CREATE");
        userService.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
     return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto dto){
        userService.updateUser(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        producer.sendEvent(userService.getUserById(id).email(), "DELETE");
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
