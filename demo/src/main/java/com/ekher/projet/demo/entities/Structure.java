package com.ekher.projet.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "structures")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long structureId;
    @Column(nullable = false,length = 100,unique = true)
    private String structureName;
}
