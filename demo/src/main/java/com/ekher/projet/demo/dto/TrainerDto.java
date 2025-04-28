package com.ekher.projet.demo.dto;

import com.ekher.projet.demo.entities.TrainerType;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainerDto {
    private Long trainerId;
    private UserDto user;

    @NotNull(message = "Trainer type cannot be null")
    private TrainerType trainerType;

    @NotNull(message = "Employer information cannot be null")
    private EmployerDto employer;
}