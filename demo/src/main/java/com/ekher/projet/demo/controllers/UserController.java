package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.TrainingDto;
import com.ekher.projet.demo.dto.ParticipantDto;
import com.ekher.projet.demo.dto.TrainerDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.models.requestData.UserRequestData;
import com.ekher.projet.demo.services.TrainingEnrollmentService;
import com.ekher.projet.demo.services.EmailService;
import com.ekher.projet.demo.services.UserService;
import com.ekher.projet.demo.utils.EnumsHelperMethods;
import com.ekher.projet.demo.utils.RandomPasswordGenerator;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final TrainingEnrollmentService trainingEnrollmentService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService,
                          TrainingEnrollmentService trainingEnrollmentService,
                          EmailService emailService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.trainingEnrollmentService = trainingEnrollmentService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    // ... autres méthodes inchangées ...

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) throws BadRequestException {
        if(userId == null){
            throw new BadRequestException("Invalid user Id, please try again");
        }
        UserDto oldUser = userService.getUserById(userId);
        if(oldUser == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No user found with id " + userId);
        }

        UserDto updatedUser = UserDto.builder()
                .username(userDto.getUsername() != null ? userDto.getUsername() : oldUser.getUsername())
                .email(userDto.getEmail() != null ? userDto.getEmail() : oldUser.getEmail())
                .password(userDto.getPassword() != null ? userDto.getPassword() : oldUser.getPassword())
                .role(userDto.getRole() != null ? userDto.getRole() : oldUser.getRole())
                .dateOfBirth(userDto.getDateOfBirth() != null ? userDto.getDateOfBirth() : oldUser.getDateOfBirth())
                .description(userDto.getDescription() != null ? userDto.getDescription() : oldUser.getDescription())
                .userId(userId)
                .gender(userDto.getGender() != null ? userDto.getGender() : oldUser.getGender())
                .phoneNumber(userDto.getPhoneNumber() != null ? userDto.getPhoneNumber() : oldUser.getPhoneNumber())
                .profilePicture(userDto.getProfilePicture() != null ? userDto.getProfilePicture() : oldUser.getProfilePicture())
                .build();

        UserDto response = userService.updateUser(updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws BadRequestException {
        if(userId==null){
            throw new BadRequestException("The provided user id is null");
        }
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{id}/enrollments")
    public ResponseEntity<List<TrainingDto>> getEnrollments(@PathVariable Long id) throws BadRequestException {
        if(id==null){
            throw new BadRequestException("The provided user id is null");
        }
        List<TrainingDto> enrollments=trainingEnrollmentService.getParticipantsEnrollment(id);
        return ResponseEntity.ok(enrollments);
    }




}
