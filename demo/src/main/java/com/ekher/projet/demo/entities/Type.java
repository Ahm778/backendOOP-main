package com.ekher.projet.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Type {
    @JsonProperty("REMOTE")  REMOTE,
    @JsonProperty("ONSITE") ONSITE,
    @JsonProperty("HYBRID") HYBRID
}