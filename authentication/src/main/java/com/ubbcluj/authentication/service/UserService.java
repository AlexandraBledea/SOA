package com.ubbcluj.authentication.service;

import com.ubbcluj.authentication.dto.JwtResponseDto;
import com.ubbcluj.authentication.dto.RegisterUserDto;
import com.ubbcluj.authentication.exception.AuthenticationException;
import com.ubbcluj.authentication.exception.ConflictException;
import com.ubbcluj.authentication.exception.EntityNotFoundException;
import com.ubbcluj.authentication.persistence.UserRepository;
import com.ubbcluj.authentication.persistence.entity.UserEntity;
import com.ubbcluj.authentication.util.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final Converter converter;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TokenService tokenService, Converter converter, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtResponseDto authenticate(String email, String password) throws EntityNotFoundException, AuthenticationException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException());

        if (passwordEncoder.matches(password, user.getPassword())) {
            return new JwtResponseDto(tokenService.generateToken(user.getUsername()), user.getUsername());
        }

        throw new AuthenticationException();
    }

    public JwtResponseDto register(RegisterUserDto registerUserDto) throws ConflictException {
        if (userRepository.existsByEmail(registerUserDto.email())) {
            throw new ConflictException();
        } else if (userRepository.existsByUsername(registerUserDto.username())) {
            throw new ConflictException();
        }
        UserEntity user = converter.convertToUserEntity(registerUserDto);
        userRepository.save(user);
        return new JwtResponseDto(tokenService.generateToken(user.getUsername()), user.getUsername());
    }
}
