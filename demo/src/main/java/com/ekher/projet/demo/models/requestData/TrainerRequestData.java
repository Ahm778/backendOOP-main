package com.ekher.projet.demo.models.requestData;

import com.ekher.projet.demo.entities.Gender;
import com.ekher.projet.demo.entities.TrainerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
@Schema(description = "Request data object for creating or updating a trainer")
public class TrainerRequestData {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(
            description = "Username of the trainer. Must be unique across the system",
            example = "johndoe",
            minLength = 3,
            maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Schema(
            description = "Email address of the trainer. Must be a valid email format",
            example = "johndoe@example.com",
            format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Schema(
            description = "Primary phone number of the trainer in international format",
            example = "+1234567890",
            minLength = 10,
            maxLength = 15,
            pattern = "^\\+[0-9]{10,15}$",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String phoneNumber;

    @Schema(
            description = "Birth date of the trainer in YYYY-MM-DD format",
            example = "1990-01-01",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Date dateOfBirth;

    @Schema(
            description = "Gender of the trainer",
            example = "MALE",
            allowableValues = {"MALE", "FEMALE", "OTHER"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Gender gender;

    @Schema(
            description = "URL pointing to the trainer's profile picture",
            example = "https://example.com/profile.jpg",
            format = "uri",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String profilePicture;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Schema(
            description = "Brief biographical description of the trainer",
            example = "A passionate fitness trainer with 8 years of experience",
            maxLength = 255,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @NotNull(message = "Trainer type cannot be null")
    @Schema(
            description = "Type classification of the trainer",
            example = "INTERNAL",
            allowableValues = {"INTERNAL", "EXTERNAL"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TrainerType trainerType;

    @NotNull(message = "Employer ID cannot be null")
    @Schema(
            description = "Unique identifier of the employer organization",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long employerId;
}