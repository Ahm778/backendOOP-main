package com.ekher.projet.demo.models.dashboardData;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingsDetails {
    private Long nbTrainings;
    private Long totalIncome;
    private List<DomainCount> trainingsPerDomain;
}