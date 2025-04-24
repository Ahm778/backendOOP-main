package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.TrainingParticipantDto;
import com.ekher.projet.demo.dto.ParticipantDto;
import com.ekher.projet.demo.dto.UserDto;

import com.ekher.projet.demo.services.TrainingEnrollmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/api/v1/trainings")
public class TrainingEnrollmentController {
    private final TrainingEnrollmentService trainingEnrollmentService;

    @Autowired
    public TrainingEnrollmentController(TrainingEnrollmentService trainingEnrollmentService) {
        this.trainingEnrollmentService = trainingEnrollmentService;
    }



    @PostMapping("/{id}/enroll/{user_id}")
    public ResponseEntity<TrainingParticipantDto> enrollTraining(@PathVariable String id, @PathVariable Long user_id) throws ResponseStatusException {
        if (id == null || user_id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message d'erreur");
        }

        TrainingParticipantDto trainingParticipantDto = trainingEnrollmentService.createTrainingEnrollment(id, user_id);
        return new ResponseEntity<>(trainingParticipantDto, HttpStatus.CREATED);
    }



    @GetMapping("/{id}/participants")
    public ResponseEntity<List<UserDto>> getTrainingParticipants(@PathVariable String id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message d'erreur");
        }
        List<UserDto> response = trainingEnrollmentService.getEnrollmentParticipants(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @DeleteMapping("/{id}/unenroll/{pid}")
    public ResponseEntity<String> unenrollTraining(@PathVariable String id, @PathVariable Long pid) {
        if (id == null || pid == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message d'erreur");
        }
        trainingEnrollmentService.cancelTrainingEnrollment(pid, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
