package com.ekher.projet.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "training_participants")
public class TrainingParticipant {

    @EmbeddedId
    private TrainingParticipantsId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("trainingId")
    @JoinColumn(name = "training_id", referencedColumnName = "trainingId")
    private Training training;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User participant;

}
