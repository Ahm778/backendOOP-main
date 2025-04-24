package com.ekher.projet.demo.dto;

import com.ekher.projet.demo.dto.ParticipantDto;
import com.ekher.projet.demo.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class TrainingParticipantDto {

    private TrainingDto training;
    private UserDto participant;
}
