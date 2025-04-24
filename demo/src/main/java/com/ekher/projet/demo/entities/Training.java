package com.ekher.projet.demo.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "trainings")
@Timestamp
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String trainingId;
    @Column(nullable = false,length = 100)
    private String title;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    private String description;
    private String domainName;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "trainer_id", referencedColumnName = "trainerId", nullable = false)
    private Trainer trainer;
}
