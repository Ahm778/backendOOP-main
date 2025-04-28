package com.ekher.projet.demo.models.dashboardData;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainersDetails {
    private Long nbTrainers;
    private int internalTrainersCount;
    private int externalTrainersCount;
    private List<TopUsers> topTrainers;

}