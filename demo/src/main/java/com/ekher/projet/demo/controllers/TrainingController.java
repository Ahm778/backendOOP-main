package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.TrainingDto;
import com.ekher.projet.demo.models.requestData.TrainingRequestData;
import com.ekher.projet.demo.services.DomainService;
import com.ekher.projet.demo.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {
    private final TrainingService trainingService;
    private final DomainService domainService;

    @Autowired
    public TrainingController(TrainingService trainingService, DomainService domainService) {
        this.trainingService = trainingService;
        this.domainService = domainService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings(
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok(trainingService.getAllTrainings(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable("id") String id) {
        return trainingService.getTrainingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingRequestData data) {
        if (data == null || data.getDomainId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return Optional.ofNullable(domainService.getDomain(data.getDomainId()))
                .flatMap(domain -> {
                    TrainingDto trainingDto = TrainingDto.builder()
                            .title(data.getTitle())
                            .startDate(data.getStartDate())
                            .endDate(data.getEndDate())
                            .description(data.getDescription())
                            .type(data.getType())
                            .price(data.getPrice())
                            .endTime(data.getEndTime())
                            .startTime(data.getStartTime())
                            .domain(domain)
                            .build();

                    return trainingService.createTraining(trainingDto, data.getTrainerId());
                })
                .map(training -> ResponseEntity.status(HttpStatus.CREATED).body(training))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TrainingDto> updateTraining(
            @PathVariable("id") String id,
            @RequestBody TrainingRequestData data) {

        if (id == null || data == null) {
            return ResponseEntity.badRequest().build();
        }

        return trainingService.getTrainingById(id)
                .flatMap(oldTraining -> {
                    TrainingDto newTraining = TrainingDto.builder()
                            .trainingId(oldTraining.getTrainingId())
                            .title(data.getTitle() != null ? data.getTitle() : oldTraining.getTitle())
                            .startDate(data.getStartDate() != null ? data.getStartDate() : oldTraining.getStartDate())
                            .endDate(data.getEndDate() != null ? data.getEndDate() : oldTraining.getEndDate())
                            .description(data.getDescription() != null ? data.getDescription() : oldTraining.getDescription())
                            .domain(data.getDomainId() != null ?
                                    domainService.getDomain(data.getDomainId()) : oldTraining.getDomain())
                            .startTime(data.getStartTime() != null ? data.getStartTime() : oldTraining.getStartTime())
                            .endTime(data.getEndTime() != null ? data.getEndTime() : oldTraining.getEndTime())
                            .price(data.getPrice() != null ? data.getPrice() : oldTraining.getPrice())
                            .type(data.getType() != null ? data.getType() : oldTraining.getType())
                            .build();

                    Long trainerId = data.getTrainerId() != null ?
                            data.getTrainerId() : oldTraining.getTrainer().getTrainerId();

                    return trainingService.createTraining(newTraining, trainerId);
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable("id") String id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (trainingService.getTrainingById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        trainingService.deleteTrainingById(id);
        return ResponseEntity.noContent().build();
    }
}