package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, String> {

}