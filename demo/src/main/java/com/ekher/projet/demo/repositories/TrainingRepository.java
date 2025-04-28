package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.Training;
import com.ekher.projet.demo.entities.TrainerType;
import com.ekher.projet.demo.models.dashboardData.DomainCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, String> {
    @Query(nativeQuery = true, value="SELECT * FROM trainings WHERE trainer_id = ?1")
    List<Training> getTrainingsByTrainerId(Long id);


    @Query(nativeQuery = true, value="SELECT d.domain_name AS domaine, COUNT(*) AS nb FROM trainings t " +
            "JOIN domains d ON t.domain_id = d.domain_id " +
            "GROUP BY d.domain_name")
    List<DomainCount> getTrainingsPerDomain();

}