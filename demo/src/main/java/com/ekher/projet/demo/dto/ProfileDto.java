package com.ekher.projet.demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ProfileDto {
    private Long profileId;
    @NotBlank(message = "Profile type cannot be blank")
    @Size(min = 1, max = 100, message = "Profile type must be between 1 and 100 characters")
    private String profileType;
}