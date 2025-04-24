package com.ekher.projet.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Trainer {
    @Id
    private Long trainerId;
    @Column(nullable = false)
    private String employerName;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TrainerType trainerType;
}

