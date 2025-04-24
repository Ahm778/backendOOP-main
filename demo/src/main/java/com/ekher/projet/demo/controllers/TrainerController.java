package com.ekher.projet.demo.controllers;

//import com.ekher.projet.demo.annotations.users.CheckInCache;
import com.ekher.projet.demo.dto.TrainerDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Role;
import com.ekher.projet.demo.models.requestData.TrainerRequestData;
import com.ekher.projet.demo.services.EmailService;
import com.ekher.projet.demo.services.TrainerService;
import com.ekher.projet.demo.utils.RandomPasswordGenerator;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController

@RequestMapping("/api/v1/trainers")
public class TrainerController {

    private final TrainerService trainerService;
    private final EmailService emailService;
    @Autowired
    public TrainerController(TrainerService trainerService,EmailService emailService) {
        this.trainerService = trainerService;
        this.emailService = emailService;
    }



    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers(@RequestParam(required = false, defaultValue = "0") Integer page) {
        List<TrainerDto> trainers = trainerService.getAllTrainers(page);
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }


    //@CheckInCache
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getTrainer(@PathVariable Long id) throws ResponseStatusException {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id);
        }

        TrainerDto trainer = trainerService.getTrainer(id);
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody TrainerRequestData data) {
        if (data == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provided trainer data is null");
        }
        String password = RandomPasswordGenerator.generateRandomPassword();
        UserDto userDto = UserDto.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .role(Role.TRAINER)
                .phoneNumber(data.getPhoneNumber())
                .profilePicture(data.getProfilePicture())
                .description(data.getDescription())
                .dateOfBirth(data.getDateOfBirth())
                .gender(data.getGender())
                .password(password)
                .build();
        TrainerDto trainerDto = TrainerDto.builder()
                .user(userDto)
                .trainerType(data.getTrainerType())
                .employerName(data.getEmployerName())
                .build();
        TrainerDto trainer = trainerService.createTrainer(trainerDto);
        emailService.sendSimpleEmail(userDto.getEmail(), "An account with this email have been created",password);
        return new ResponseEntity<>(trainer, HttpStatus.CREATED);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<TrainerDto> updateTrainer(@PathVariable Long id, @RequestBody TrainerRequestData data) throws BadRequestException {
        if (data == null) {
            throw new BadRequestException("The provided trainer is null");
        }
        if (id == null) {
            throw new BadRequestException("The provided trainer id is null");
        }
        TrainerDto oldTrainer = trainerService.getTrainer(id);
        if (oldTrainer == null) {
            throw new NoSuchElementException("Trainer not found");
        }
        UserDto oldUser = oldTrainer.getUser();
        UserDto newUser = UserDto.builder()
                .username(data.getUsername() != null ? data.getUsername() : oldUser.getUsername())
                .email(data.getEmail() != null ? data.getEmail() : oldUser.getEmail())
                .dateOfBirth(data.getDateOfBirth() != null ? data.getDateOfBirth() : oldUser.getDateOfBirth())
                .description(data.getDescription() != null ? data.getDescription() : oldUser.getDescription())
                .password(oldUser.getPassword())
                .role(Role.TRAINER)
                .userId(oldUser.getUserId())
                .gender(data.getGender() != null ? data.getGender() : oldUser.getGender())
                .phoneNumber(data.getPhoneNumber() != null ? data.getPhoneNumber() : oldUser.getPhoneNumber())
                .profilePicture(data.getProfilePicture() != null ? data.getProfilePicture() : oldUser.getProfilePicture())
                .build();
        TrainerDto newTrainer = TrainerDto.builder()
                .trainerId(oldTrainer.getTrainerId())
                .user(oldUser)
                .trainerType(data.getTrainerType() != null ? data.getTrainerType() : oldTrainer.getTrainerType())
                .employerName(data.getEmployerName() != null ? data.getEmployerName() : oldTrainer.getEmployerName())
                .build();
        TrainerDto trainer = trainerService.updateTrainer(newTrainer, newUser);
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrainer(@PathVariable Long id) throws BadRequestException {
        if (id == null) {
            throw new BadRequestException("Trainer id cannot be null");
        }
        trainerService.deleteTrainer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}