package com.ekher.projet.demo.mappers;

import com.ekher.projet.demo.dto.TrainingDto;
import com.ekher.projet.demo.entities.Training;
import com.ekher.projet.demo.mappers.TrainerMapper;

public class TrainingMapper {
    public static Training toEntity(TrainingDto trainingDto) {
        return Training.builder()
                .trainingId(trainingDto.getTrainingId())
                .description(trainingDto.getDescription())
                .startDate(trainingDto.getStartDate())
                .endDate(trainingDto.getEndDate())
                .title(trainingDto.getTitle())
                .type(trainingDto.getType())
                .startTime(trainingDto.getStartTime())
                .endTime(trainingDto.getEndTime())
                .price(trainingDto.getPrice())
                .domain(DomainMapper.toEntity(trainingDto.getDomain()))
                .trainer(TrainerMapper.toEntity(trainingDto.getTrainer()))
                .build();
    }
    public static TrainingDto toDto(Training training) {
        return TrainingDto.builder()
                .trainingId(training.getTrainingId())
                .description(training.getDescription())
                .startDate(training.getStartDate())
                .endDate(training.getEndDate())
                .startTime(training.getStartTime())
                .endTime(training.getEndTime())
                .price(training.getPrice())
                .title(training.getTitle())
                .type(training.getType())
                .domain(DomainMapper.toDto(training.getDomain()))
                .trainer(TrainerMapper.toDto(training.getTrainer()))
                .build();
    }
}