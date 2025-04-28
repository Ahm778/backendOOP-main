package com.ekher.projet.demo.models.requestData;

import com.ekher.projet.demo.entities.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Schema(description = "Request data object for creating or updating a training")
public class TrainingRequestData {

    @NotBlank(message = "Title cannot be blank")
    @Schema(
            description = "Title of the training",
            example = "Strength Training Workshop",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @NotNull(message = "Start date cannot be null")
    @Schema(
            description = "Start date of the training",
            example = "2023-10-01",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Date startDate;
    @NotNull(message = "Price cannot be null")
    @Schema(
            description = "Price of the training in the smallest currency unit (e.g., cents)",
            example = "50000", // 500.00 in display
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long price;
    @NotNull(message = "End date cannot be null")
    @Schema(
            description = "End date of the training",
            example = "2023-10-05",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Date endDate;

    @NotNull(message = "Start time cannot be null")
    @Schema(
            description = "Start time of the training",
            example = "09:00",
            type = "string",
            format = "time",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    @Schema(
            description = "End time of the training",
            example = "12:00",
            type = "string",
            format = "time",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalTime endTime;

    @Schema(
            description = "Description of the training",
            example = "A comprehensive workshop on strength training techniques",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @NotBlank(message = "Domain name cannot be blank")
    @Schema(
            description = "Name of the domain associated with the training",
            example = "Strength Training",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long domainId;

    @NotNull(message = "Training type cannot be null")
    @Schema(
            description = "Type of the training (only applicable if type is HYBRID, REMOTE, ONSITE)",
            example = "HYBRID",
            allowableValues = {"HYBRID", "REMOTE", "ONSITE"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Type type;

    @NotNull(message = "Trainer ID cannot be null")
    @Schema(
            description = "ID of the trainer responsible for the training",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long trainerId;
}