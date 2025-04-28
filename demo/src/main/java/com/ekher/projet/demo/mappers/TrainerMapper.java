package com.ekher.projet.demo.mappers;

import com.ekher.projet.demo.dto.TrainerDto;
import com.ekher.projet.demo.entities.Trainer;


public class TrainerMapper {
    public static TrainerDto toDto(Trainer trainer) {
        return TrainerDto.builder()
                .trainerId(trainer.getTrainerId())
                .user(UserMapper.toDto(trainer.getUser()))
                .trainerType(trainer.getTrainerType())
                .employer(EmployerMapper.toDto(trainer.getEmployer()))
                .build();
    }
    public static TrainerDto toLightDto(Trainer trainer) {
        return TrainerDto.builder()
                .trainerId(trainer.getTrainerId())
                .user(UserMapper.toLightDto(trainer.getUser()))
                .trainerType(trainer.getTrainerType())
                .employer(EmployerMapper.toDto(trainer.getEmployer()))
                .build();
    }
    public static Trainer toEntity(TrainerDto trainerDto) {
        return Trainer.builder()
                .trainerId(trainerDto.getTrainerId())
                .user(UserMapper.toEntity(trainerDto.getUser()))
                .trainerType(trainerDto.getTrainerType())
                .employer(EmployerMapper.toEntity(trainerDto.getEmployer()))
                .build();
    }
    public static TrainerDto toLightestDto(Trainer trainer) {
        return TrainerDto.builder()
                .trainerId(trainer.getTrainerId())
                .user(UserMapper.toLightestDto(trainer.getUser()))
                .build();
    }
}