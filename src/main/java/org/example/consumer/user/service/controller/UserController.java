package org.example.consumer.user.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumer.user.service.schema.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    @GetMapping("/getAllUsers")
    public List<UserDTO> getAllUsers() {
        return userClient.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") String id) {
        return userClient.getUserById(id);
    }

}
