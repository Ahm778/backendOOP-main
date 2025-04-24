package com.ekher.projet.demo.dto;

import com.ekher.projet.demo.dto.TrainerDto;
import com.ekher.projet.demo.entities.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class TrainingDto implements Serializable {
    private static final long serialVersionUID = 1L;


    private String trainingId;

    @NotBlank(message = "Training title cannot be blank")

    private String title;

    @NotNull(message = "Start date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")

    private Date startDate;

    @NotNull(message = "End date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")

    private Date endDate;
    @NotNull(message = "Start time cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;
    @NotNull(message = "End time cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;
    private String description;
    @NotBlank(message = "Domain name cannot be blank")
    private String domainName;
    @NotNull(message = "Training type cannot be null")
    private Type type;
    @JsonIgnoreProperties({
            "userId", "password", "role", "isVerified", "phoneNumber",
            "secondPhoneNumber", "dateOfBirth", "description", "gender"
    })
    private TrainerDto trainer;
    @NotNull(message = "Price cannot be null")
    private Long price;
}
