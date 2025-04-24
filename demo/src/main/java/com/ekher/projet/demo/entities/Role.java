package com.ekher.projet.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {
    @JsonProperty("ADMIN")  ADMIN,
    @JsonProperty("MANAGER")    MANAGER,
    @JsonProperty("PARTICIPANT")   PARTICIPANT,
    @JsonProperty("TRAINER")   TRAINER,

}