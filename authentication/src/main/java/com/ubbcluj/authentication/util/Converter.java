package com.ubbcluj.authentication.util;

import com.ubbcluj.authentication.dto.RegisterUserDto;
import com.ubbcluj.authentication.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    private final PasswordEncoder passwordEncoder;

    public Converter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity convertToUserEntity(RegisterUserDto registerUserDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerUserDto.username());
        userEntity.setPassword(passwordEncoder.encode(registerUserDto.password()));
        userEntity.setEmail(registerUserDto.email());
        userEntity.setDateOfBirth(registerUserDto.dateOfBirth());
        return userEntity;
    }
}
