package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.models.requestData.LoginRequestData;
import com.ekher.projet.demo.models.requestData.UserRequestData;
import com.ekher.projet.demo.security.JwtUtils;
import com.ekher.projet.demo.services.UserService;
import com.ekher.projet.demo.utils.EnumsHelperMethods;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final  PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestData data) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getEmail(),
                        data.getPassword()
                )
        );

        authentication.getPrincipal();
        UserDto userDto = userService.getUserByEmail(data.getEmail());
        String token = jwtUtils.generateToken(userDto);
        Map<String, Object> response = new HashMap<>();
        response.put("email", userDto.getEmail());
        response.put("token", token);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestData data) throws BadRequestException {
        boolean wrongRequest=data==null || data.getUsername()==null || data.getEmail()==null || !EnumsHelperMethods.isValidRole(data.getRole());
        if(wrongRequest){
            throw new BadRequestException("Invalid input data, please try again");
        }
        UserDto userDto=UserDto.builder()
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
        UserDto response=userService.createUser(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

