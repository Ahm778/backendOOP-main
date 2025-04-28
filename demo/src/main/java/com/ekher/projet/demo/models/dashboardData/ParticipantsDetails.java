package com.ekher.projet.demo.models.dashboardData;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantsDetails {
    private Long nbParticipants;
    private List<TopUsers> topParticipants;
    private List<TopUsers> topParticipantsWithDomains;

}