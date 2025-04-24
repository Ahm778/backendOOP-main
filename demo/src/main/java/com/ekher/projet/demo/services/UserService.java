package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.mappers.UserMapper;
import com.ekher.projet.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
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

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto)));
    }

    public UserDto updateUser(UserDto userDto) {
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto)));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserDto getUserByEmail(String email) {
        return UserMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + email)));
    }
}
