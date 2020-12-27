package org.example.user.service.controller;

import org.example.user.service.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Component
public class UserClient {

    private final WebClient webClient = WebClient.create();

    public UserDTO getUserById(String id) {
        return webClient.get().
                uri("/{id}", id).
                retrieve().
                bodyToMono(UserDTO.class).
                onErrorMap(WebClientResponseException.BadRequest.class,
                           e -> new Exception("Insufficient authentication to access task API.", e)).
                block();
    }

    @SuppressWarnings("java:S2583") // SonarQube seems to get this wrong, block can return null if the Mono is empty.
    public List<UserDTO> getAllUser() {
        UserDTO[] userArray = webClient.get().retrieve().bodyToMono(UserDTO[].class).block();
        if (userArray != null) {
            return asList(userArray);
        }
        else {
            return emptyList();
        }
    }

}
