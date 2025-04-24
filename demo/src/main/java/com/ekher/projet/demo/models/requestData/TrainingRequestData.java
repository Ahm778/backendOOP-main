package com.ekher.projet.demo.models.requestData;

import com.ekher.projet.demo.entities.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;
import java.util.Date;

@Getter

public class TrainingRequestData {

    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
    @NotNull(message = "Price cannot be null")
    private Long price;
    @NotNull(message = "End date cannot be null")
    private Date endDate;
    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;
    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;
    private String description;
    @NotBlank(message = "Domain name cannot be blank")
    private String domainName;
    @NotNull(message = "Training type cannot be null")
    private Type type;
    @NotNull(message = "Trainer ID cannot be null")
    private Long trainerId;
}
