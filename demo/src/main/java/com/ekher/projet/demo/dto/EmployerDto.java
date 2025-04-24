package com.ekher.projet.demo.dto;




import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmployerDto {


    private Long id;

    @NotBlank(message = "Employer name cannot be blank")
    @Size(min = 1, max = 255, message = "Employer name must be between 1 and 255 characters")

    private String employerName;
}