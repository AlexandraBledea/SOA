package com.ubbcluj.authentication.controller;

import com.ubbcluj.authentication.dto.AuthenticationDto;
import com.ubbcluj.authentication.dto.JwkSetResponseDto;
import com.ubbcluj.authentication.dto.JwtResponseDto;
import com.ubbcluj.authentication.dto.RegisterUserDto;
import com.ubbcluj.authentication.exception.ConflictException;
import com.ubbcluj.authentication.exception.EntityNotFoundException;
import com.ubbcluj.authentication.exception.AuthenticationException;
import com.ubbcluj.authentication.service.TokenService;
import com.ubbcluj.authentication.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/jwks")
    public JwkSetResponseDto getJwks() {
        return tokenService.getJwks();
    }

    @PostMapping("/login")
    public JwtResponseDto authenticate(@RequestBody AuthenticationDto authenticateUserDTO) throws AuthenticationException, EntityNotFoundException {
        return userService.authenticate(authenticateUserDTO.email(), authenticateUserDTO.password());
    }

    @PostMapping("/register")
    public JwtResponseDto register(@RequestBody RegisterUserDto registerUserDTO) throws ConflictException {
        return userService.register(registerUserDTO);
    }
}
