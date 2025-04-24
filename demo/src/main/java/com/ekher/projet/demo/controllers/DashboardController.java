package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.models.dashboardData.ParticipantsDetails;
import com.ekher.projet.demo.models.dashboardData.TrainersDetails;
import com.ekher.projet.demo.models.dashboardData.TrainingsDetails;
import com.ekher.projet.demo.services.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    @GetMapping("/participants")
    public ResponseEntity<ParticipantsDetails> getParticipants() {
        ParticipantsDetails details= dashboardService.getParticipantsDetails();
        return ResponseEntity.ok(details);

    }
    @GetMapping("/trainers")
    public ResponseEntity<TrainersDetails> getTrainers() {
        TrainersDetails details= dashboardService.getTrainersDetails();
        return ResponseEntity.ok(details);
    }
    @GetMapping("/trainings")
    public ResponseEntity<TrainingsDetails> getTrainings() {
        TrainingsDetails details= dashboardService.getTrainingsDetails();
        return ResponseEntity.ok(details);
    }
}

