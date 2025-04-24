package com.ekher.projet.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;
    @Column(nullable = false,length = 100,unique = true)
    private String profileType;
}
