package com.ubbcluj.task.service;

import com.ubbcluj.task.dto.UserDto;
import com.ubbcluj.task.exception.EntityNotFoundException;
import com.ubbcluj.task.persistence.UserRepository;
import com.ubbcluj.task.persistence.entity.UserEntity;
import com.ubbcluj.task.utils.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Converter converter;

    public UserService(UserRepository userRepository, Converter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(converter::convertToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUser(String username) throws EntityNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        return converter.convertToUserDto(userEntity);
    }
}
