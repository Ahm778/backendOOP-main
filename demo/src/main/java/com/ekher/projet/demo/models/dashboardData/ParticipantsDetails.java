package com.ekher.projet.demo.models.dashboardData;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ParticipantsDetails {
    private Long nbParticipants;
    private List<TopUsers> topParticipants;
    private List<TopUsers> topParticipantsWithDomains;

}