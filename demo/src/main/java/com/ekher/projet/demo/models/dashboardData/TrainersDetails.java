package com.ekher.projet.demo.models.dashboardData;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TrainersDetails {
    private Long nbTrainers;
    private int internalTrainersCount;
    private int externalTrainersCount;
    private List<TopUsers> topTrainers;

}