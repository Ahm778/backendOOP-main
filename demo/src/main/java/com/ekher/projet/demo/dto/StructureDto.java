package com.ekher.projet.demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class StructureDto {

    private Long structureId;
    @NotBlank(message = "Structure name cannot be blank")
    @Size(min = 1, max = 100, message = "Structure name must be between 1 and 100 characters")
    private String structureName;
}
