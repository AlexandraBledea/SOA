package com.ubbcluj.task.utils;

import com.ubbcluj.task.dto.UserDto;
import com.ubbcluj.task.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public UserDto convertToUserDto(UserEntity userEntity) {
        return new UserDto(userEntity.getUsername(), userEntity.getEmail(), userEntity.getDateOfBirth());
    }
}
