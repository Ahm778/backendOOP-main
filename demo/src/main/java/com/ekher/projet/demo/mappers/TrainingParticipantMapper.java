package com.ekher.projet.demo.mappers;

import com.ekher.projet.demo.dto.TrainingParticipantDto;
import com.ekher.projet.demo.entities.TrainingParticipant;
import com.ekher.projet.demo.mappers.UserMapper;

public class TrainingParticipantMapper {
    public static   TrainingParticipant toEntity(TrainingParticipantDto trainingParticipantDto) {
        return TrainingParticipant.builder()
                .participant(UserMapper.toEntity(trainingParticipantDto.getParticipant()))
                .training(TrainingMapper.toEntity(trainingParticipantDto.getTraining()))
                .build();
    }
    public static TrainingParticipantDto toDto(TrainingParticipant trainingParticipant) {
        return TrainingParticipantDto.builder()
                .training(TrainingMapper.toDto(trainingParticipant.getTraining()))
                .participant(UserMapper.toLightDto(trainingParticipant.getParticipant()))
                .build();
    }

}