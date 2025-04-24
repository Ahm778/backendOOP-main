package com.ekher.projet.demo.models.dashboardData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Setter;

@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public  class TopUsers {
    private  String username;
    private  long nb;
    private  String Domain;
}
