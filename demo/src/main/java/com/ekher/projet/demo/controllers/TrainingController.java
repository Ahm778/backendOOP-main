package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.TrainingDto;
import com.ekher.projet.demo.models.requestData.TrainingRequestData;
import com.ekher.projet.demo.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/api/v1/trainings")
public class TrainingController {
    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }



    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings(@RequestParam(required = false, defaultValue = "0") Integer page) {
        List<TrainingDto> trainings = trainingService.getAllTrainings(page);
        return ResponseEntity.ok(trainings);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable("id") String id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Training id cannot be null");
        }
        TrainingDto training = trainingService.getTrainingById(id);
        if (training == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Training not found");
        }
        return ResponseEntity.ok(training);
    }



    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingRequestData data) {
        if (data == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request data cannot be null");
        }

        TrainingDto trainingDto = TrainingDto.builder()
                .title(data.getTitle())
                .startDate(data.getStartDate())
                .endDate(data.getEndDate())
                .description(data.getDescription())
                .type(data.getType())
                .price(data.getPrice())
                .endTime(data.getEndTime())
                .startTime(data.getStartTime())
                .domainName(data.getDomainName())
                .build();

        TrainingDto savedTraining = trainingService.createTraining(trainingDto, data.getTrainerId());
        return new ResponseEntity<>(savedTraining, HttpStatus.CREATED);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable("id") String id, @RequestBody TrainingRequestData data) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Training id cannot be null");
        }
        if (data == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request data cannot be null");
        }

        TrainingDto oldTraining = trainingService.getTrainingById(id);
        if (oldTraining == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Training not found");
        }

        TrainingDto newTraining = TrainingDto.builder()
                .trainingId(oldTraining.getTrainingId())
                .title(data.getTitle() != null ? data.getTitle() : oldTraining.getTitle())
                .startDate(data.getStartDate() != null ? data.getStartDate() : oldTraining.getStartDate())
                .endDate(data.getEndDate() != null ? data.getEndDate() : oldTraining.getEndDate())
                .description(data.getDescription() != null ? data.getDescription() : oldTraining.getDescription())
                .domainName(data.getDomainName() != null ? data.getDomainName() : oldTraining.getDomainName())
                .startTime(data.getStartTime() != null ? data.getStartTime() : oldTraining.getStartTime())
                .endTime(data.getEndTime() != null ? data.getEndTime() : oldTraining.getEndTime())
                .price(data.getPrice() != null ? data.getPrice() : oldTraining.getPrice())
                .type(data.getType() != null ? data.getType() : oldTraining.getType())
                .build();

        Long trainerId = data.getTrainerId() != null ? data.getTrainerId() : oldTraining.getTrainer().getTrainerId();
        TrainingDto updatedTraining = trainingService.updateTraining(newTraining, trainerId);
        return ResponseEntity.ok(updatedTraining);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable("id") String id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Training id cannot be null");
        }
        trainingService.deleteTrainingById(id);
        return ResponseEntity.noContent().build();
    }
}