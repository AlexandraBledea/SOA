package com.ubbcluj.task.controller;

import com.ubbcluj.task.config.LogToKafka;
import com.ubbcluj.task.dto.UserDto;
import com.ubbcluj.task.exception.EntityNotFoundException;
import com.ubbcluj.task.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @LogToKafka
    @GetMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    public UserDto getUser(@PathVariable String username) throws EntityNotFoundException {
        return userService.getUser(username);
    }

    @LogToKafka
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }
}
