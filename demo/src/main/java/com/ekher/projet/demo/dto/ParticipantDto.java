package com.ekher.projet.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {
    private Long participantId;

    private UserDto user;

    @NotBlank(message = "Structure name cannot be blank")
    private StructureDto structure;

    @NotBlank(message = "Profile cannot be blank")
    private ProfileDto profile;
}