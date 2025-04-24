package com.ekher.projet.demo.mappers;

import com.ekher.projet.demo.dto.TrainerDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Trainer;
import com.ekher.projet.demo.entities.User;

public class TrainerMapper {
    public static TrainerDto toDto(Trainer trainer) {
        return TrainerDto.builder()
                .trainerId(trainer.getTrainerId())
                .user(UserMapper.toDto(trainer.getUser()))
                .trainerType(trainer.getTrainerType())
                .employerName(trainer.getEmployerName())
                .build();
    }
    public static TrainerDto toLightDto(Trainer trainer) {
        return TrainerDto.builder()
                .trainerId(trainer.getTrainerId())
                .user(UserMapper.toLightDto(trainer.getUser()))
                .trainerType(trainer.getTrainerType())
                .employerName(trainer.getEmployerName())
                .build();
    }
    public static Trainer toEntity(TrainerDto trainerDto) {
        return Trainer.builder()
                .trainerId(trainerDto.getTrainerId())
                .user(UserMapper.toEntity(trainerDto.getUser()))
                .trainerType(trainerDto.getTrainerType())
                .employerName(trainerDto.getEmployerName())
                .build();
    }
}
