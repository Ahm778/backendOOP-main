package com.ekher.projet.demo.models.requestData;



import com.ekher.projet.demo.entities.Gender;
import com.ekher.projet.demo.entities.TrainerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter

public class TrainerRequestData {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phoneNumber;
    private Date dateOfBirth;
    private Gender gender;
    private String profilePicture;
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    @NotBlank(message = "Trainer type cannot be blank")
    private TrainerType trainerType;
    @NotBlank(message = "Employer name cannot be blank")
    private String employerName;
}
