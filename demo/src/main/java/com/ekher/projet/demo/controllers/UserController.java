package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.TrainingDto;
import com.ekher.projet.demo.dto.ParticipantDto;
import com.ekher.projet.demo.dto.TrainerDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Role;
import com.ekher.projet.demo.models.requestData.UserRequestData;
import com.ekher.projet.demo.services.TrainingEnrollmentService;
import com.ekher.projet.demo.services.UserService;
import com.ekher.projet.demo.utils.EnumsHelperMethods;
import com.ekher.projet.demo.utils.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final TrainingEnrollmentService trainingEnrollmentService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService,
                          TrainingEnrollmentService trainingEnrollmentService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.trainingEnrollmentService = trainingEnrollmentService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(userService.getAllUsers(page));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.of(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestData data) {
        if (data == null || data.getUsername() == null || data.getEmail() == null ||
                !EnumsHelperMethods.isValidRole(data.getRole())) {
            return ResponseEntity.badRequest().build();
        }

        String password = RandomPasswordGenerator.generateRandomPassword();
        UserDto userDto = UserDto.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(passwordEncoder.encode(password))
                .role(data.getRole())
                .description(data.getDescription())
                .dateOfBirth(data.getDateOfBirth())
                .phoneNumber(data.getPhoneNumber())
                .gender(data.getGender())
                .profilePicture(data.getProfilePicture())
                .build();

        UserDto response = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<? extends Object> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        return userService.getUserById(userId)
                .map(oldUser -> {
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

                    return ResponseEntity.ok(userService.updateUser(updatedUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        if (userService.getUserById(userId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/enrollments")
    public ResponseEntity<List<TrainingDto>> getEnrollments(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(trainingEnrollmentService.getParticipantsEnrollment(id));
    }

    @GetMapping("/managers")
    public ResponseEntity<List<UserDto>> getAllManagers() {
        return ResponseEntity.ok(userService.getUsersByRole(Role.MANAGER));
    }
}