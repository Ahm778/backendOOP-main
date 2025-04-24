package com.ekher.projet.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TrainerType {
    @JsonProperty("INTERNAL")  INTERNAL,
    @JsonProperty("EXTERNAL")  EXTERNAL,
}