package org.example.springboot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@Slf4j
@RequiredArgsConstructor
class UserController {

    private final UserClient userClient;

    @GetMapping()
    public List<UserDTO> getAllUser() {
        return userClient.getAllUser();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") String id) {
        return userClient.getUserById(id);
    }

}
