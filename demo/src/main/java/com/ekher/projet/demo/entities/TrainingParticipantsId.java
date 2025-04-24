package com.ekher.projet.demo.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class TrainingParticipantsId implements Serializable {

    private String trainingId;
    private Long userId;



    public TrainingParticipantsId() {}

    public TrainingParticipantsId(String trainingId, Long userId) {
        this.trainingId = trainingId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingParticipantsId that = (TrainingParticipantsId) o;
        return Objects.equals(trainingId, that.trainingId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, userId);
    }
}
