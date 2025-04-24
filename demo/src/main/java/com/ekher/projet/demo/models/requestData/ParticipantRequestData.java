package com.ekher.projet.demo.models.requestData;



import com.ekher.projet.demo.entities.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter

public class ParticipantRequestData {
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
    @NotBlank(message = "Structure name cannot be blank")
    @Size(min = 1, max = 100, message = "Structure name must be between 1 and 100 characters")
    private String structure;
    @NotBlank(message = "Profile cannot be blank")
    @Size(min = 1, max = 100, message = "Profile must be between 1 and 100 characters")
    private String profile;
}