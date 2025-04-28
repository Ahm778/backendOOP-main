package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.TrainingDto;
import com.ekher.projet.demo.dto.TrainerDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Role;
import com.ekher.projet.demo.models.requestData.TrainerRequestData;
import com.ekher.projet.demo.services.TrainingService;
import com.ekher.projet.demo.services.EmailService;
import com.ekher.projet.demo.services.EmployerService;
import com.ekher.projet.demo.services.TrainerService;
import com.ekher.projet.demo.utils.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/trainers")
public class TrainerController {

    private final TrainerService trainerService;
    private final EmailService emailService;
    private final TrainingService trainingService;
    private final EmployerService employerService;

    @Autowired
    public TrainerController(TrainerService trainerService, EmailService emailService,
                             TrainingService trainingService, EmployerService employerService) {
        this.trainerService = trainerService;
        this.emailService = emailService;
        this.employerService = employerService;
        this.trainingService = trainingService;
    }

    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok(trainerService.getAllTrainers(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getTrainer(@PathVariable Long id) {
        Optional<TrainerDto> trainerOptional = trainerService.getTrainer(id);
        return trainerOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody TrainerRequestData data) {
        if (data == null || data.getEmployerId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return employerService.getEmployerById(data.getEmployerId())
                .map(employer -> {
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
                            .employer(employer)
                            .build();

                    TrainerDto createdTrainer = trainerService.createTrainer(trainerDto);
                    emailService.sendSimpleEmail(userDto.getEmail(), "Account created", password);
                    return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainer);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<TrainerDto>> updateTrainer(@PathVariable Long id, @RequestBody TrainerRequestData data) {
        if (data == null) {
            return ResponseEntity.badRequest().build();
        }

        return trainerService.getTrainer(id)
                .map(oldTrainer -> {
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

                    TrainerDto updatedTrainer = TrainerDto.builder()
                            .trainerId(oldTrainer.getTrainerId())
                            .user(newUser)
                            .trainerType(data.getTrainerType() != null ? data.getTrainerType() : oldTrainer.getTrainerType())
                            .employer(data.getEmployerId() != null ?
                                    employerService.getEmployerById(data.getEmployerId()).orElse(oldTrainer.getEmployer())
                                    : oldTrainer.getEmployer())
                            .build();

                    return ResponseEntity.ok(trainerService.updateTrainer(updatedTrainer, newUser));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/trainings")
    public ResponseEntity<List<TrainingDto>> getTrainings(@PathVariable Long id) {
        return ResponseEntity.ok(trainingService.getTrainingsByTrainerId(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrainerDto>> getAllTrainersList() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        Optional<TrainerDto> trainer = trainerService.getTrainer(id);
        if (trainer.isPresent()) {
            trainerService.deleteTrainer(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}