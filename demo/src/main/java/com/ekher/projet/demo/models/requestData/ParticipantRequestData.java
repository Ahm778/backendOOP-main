package com.ekher.projet.demo.models.requestData;

import com.ekher.projet.demo.entities.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
@Schema(description = "Request data object for creating or updating a participant")
public class ParticipantRequestData {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(
            description = "Username of the participant. Must be unique across the system",
            example = "johndoe",
            minLength = 3,
            maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Schema(
            description = "Email address of the participant. Must be a valid email format",
            example = "johndoe@example.com",
            format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Schema(
            description = "Primary phone number of the participant in international format",
            example = "+1234567890",
            minLength = 10,
            maxLength = 15,
            pattern = "^\\+[0-9]{10,15}$",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String phoneNumber;

    @Schema(
            description = "Birth date of the participant in YYYY-MM-DD format",
            example = "1990-01-01",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Date dateOfBirth;

    @Schema(
            description = "Gender of the participant",
            example = "MALE",
            allowableValues = {"MALE", "FEMALE", "OTHER"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Gender gender;

    @Schema(
            description = "URL pointing to the participant's profile picture",
            example = "https://example.com/profile.jpg",
            format = "uri",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String profilePicture;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Schema(
            description = "Brief biographical description of the participant",
            example = "A passionate participant in fitness programs with 5 years of experience",
            maxLength = 255,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @NotNull(message = "Structure ID cannot be null")
    @Schema(
            description = "Unique identifier of the structure associated with the participant",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long structureId;

    @NotNull(message = "Profile ID cannot be null")
    @Schema(
            description = "Unique identifier of the participant's profile",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long profileId;
}