package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.Trainer;
import com.ekher.projet.demo.entities.TrainerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    int countByTrainerType(TrainerType trainerType);
}
