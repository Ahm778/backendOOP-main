package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.mappers.UserMapper;
import com.ekher.projet.demo.entities.Role;
import com.ekher.projet.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Value("${spring.application.offset}")
    private int offset;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, offset))
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(UserMapper::toDto);
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto)));
    }

    public Optional<UserDto> updateUser(UserDto userDto) {
        if (userDto.getUserId() == null || !userRepository.existsById(userDto.getUserId())) {
            return Optional.empty();
        }
        return Optional.of(UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto))));
    }

    public boolean deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            return false;
        }
        userRepository.deleteById(userId);
        return true;
    }

    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDto);
    }

    public List<UserDto> getUsersByRole(Role role) {
        return userRepository.findAllByRole(role)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}