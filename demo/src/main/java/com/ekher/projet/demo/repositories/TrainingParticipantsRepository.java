package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.TrainingParticipant;
import com.ekher.projet.demo.entities.TrainingParticipantsId;
import com.ekher.projet.demo.models.dashboardData.TopUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingParticipantsRepository extends JpaRepository<TrainingParticipant, TrainingParticipantsId> {

    @Query(nativeQuery = true,value = "SELECT * FROM training_participants WHERE training_id=?1")
    List<TrainingParticipant> findAllByTrainingId(String trainingId);
    @Query(nativeQuery = true,value = "SELECT * FROM training_participants WHERE user_id=?1")
    List<TrainingParticipant> findTrainingsForParticipant(Long userId);
    @Query(nativeQuery = true,value="SELECT (SELECT price FROM trainings WHERE training_id=?1) * COUNT(*) FROM training_participants WHERE training_id=?1 GROUP BY training_id")
    long getIncomePerTraining(String trainingId);
    @Query(nativeQuery = true,value = "SELECT training_id FROM training_participants; ")
    List<String> getAllTrainingIds();
    @Query(nativeQuery = true, value =
            "SELECT u.user_id, u.username, COUNT(t.training_id) AS training_count " +
                    "FROM users u " +
                    "JOIN trainings t ON u.user_id = t.trainer_id " +
                    "GROUP BY u.user_id, u.username " +
                    "ORDER BY training_count DESC " +
                    "LIMIT 3")
    List<TopUsers> getTopTrainers();

    @Query(nativeQuery = true, value =
            "SELECT u.user_id, u.username, COUNT(tp.user_id) AS participant_count " +
                    "FROM users u JOIN training_participants tp ON u.user_id = tp.user_id " +
                    "GROUP BY u.user_id, u.username ORDER BY participant_count DESC LIMIT 3")
    List<TopUsers> getTopParticipants();

    @Query(nativeQuery = true, value =
            "WITH top_users AS ( " +
                    "    SELECT u.user_id, u.username, COUNT(tp.training_id) AS total_training_count " +
                    "    FROM users u " +
                    "    JOIN training_participants tp ON u.user_id = tp.user_id " +
                    "    GROUP BY u.user_id, u.username " +
                    "    ORDER BY total_training_count DESC " +
                    "    LIMIT 3 " +
                    ") " +
                    "SELECT tu.user_id, tu.username, t.domain_name, COUNT(tp.training_id) AS domain_training_count " +
                    "FROM top_users tu " +
                    "JOIN training_participants tp ON tu.user_id = tp.user_id " +
                    "JOIN trainings t ON tp.training_id = t.training_id " +
                    "GROUP BY tu.user_id, tu.username, t.domain_name " +
                    "ORDER BY tu.username, domain_training_count DESC")
    List<TopUsers> getTopParticipantsWithDomains();
}
