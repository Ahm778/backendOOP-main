package com.ekher.projet.demo.models.dashboardData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopUsers {

    private Long userId;
    private String username;
    private Long count;
    private String domain;
    private Long domainTrainingCount;

    public TopUsers(Long userId, String username, Long count) {
        this.userId = userId;
        this.username = username;
        this.count = count;
    }

    public TopUsers(Long userId, String username, String domain, Long count) {
        this.userId = userId;
        this.username = username;
        this.domain = domain;
        this.count = count;
    }
    public TopUsers(Long userId, String username, String domain, Long count, Long domainTrainingCount) {
        this.userId = userId;
        this.username = username;
        this.domain = domain;
        this.count = count;
        this.domainTrainingCount = domainTrainingCount;
    }

    public TopUsers() {
    }

}