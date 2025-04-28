package com.ekher.projet.demo.mappers;

import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.User;

public class UserMapper {
    public static User  toEntity(UserDto dto){
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .userId(dto.getUserId())
                .dateOfBirth(dto.getDateOfBirth())
                .description(dto.getDescription())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .phoneNumber(dto.getPhoneNumber())

                .role(dto.getRole())
                .build();
    }
    public static UserDto toDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .userId(user.getUserId())
                .dateOfBirth(user.getDateOfBirth())
                .description(user.getDescription())
                .email(user.getEmail())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())

                .role(user.getRole())
                .build();
    }
    public static UserDto toLightDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
    public static UserDto toLightestDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .build();
    }
}