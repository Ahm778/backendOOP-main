package com.ekher.projet.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "domains")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long domainId;
    @Column(nullable = false,length = 100,unique = true)
    private String domainName;
}