package com.ekher.projet.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "participants")
public class Participant {
    @Id
    private Long participantId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;
    @Column(nullable = false,length = 100)
    private String structure ;
    @Column(nullable = false,length = 100)
    private String profile;

}