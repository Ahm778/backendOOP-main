package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.models.requestData.LoginRequestData;
import com.ekher.projet.demo.models.requestData.UserRequestData;
import com.ekher.projet.demo.security.JwtUtils;
import com.ekher.projet.demo.services.UserService;
import com.ekher.projet.demo.utils.EnumsHelperMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserService userService,
                                    PasswordEncoder passwordEncoder,
                                    JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestData data) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.getEmail(),
                            data.getPassword()
                    )
            );

            Optional<UserDto> userOptional = userService.getUserByEmail(data.getEmail());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            UserDto userDto = userOptional.get();
            String token = jwtUtils.generateToken(userDto);
            Map<String, Object> response = new HashMap<>();
            response.put("email", userDto.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestData data) {
        if (data == null || data.getUsername() == null || data.getEmail() == null ||
                !EnumsHelperMethods.isValidRole(data.getRole())) {
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        UserDto userDto = UserDto.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(passwordEncoder.encode(data.getPassword()))
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
}