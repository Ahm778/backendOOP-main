package com.ekher.projet.demo.models.dashboardData;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DomainCount {
    private String domainName;
    private Long count;
}