package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Modifying
    @Query(value = "UPDATE participants p " +
            "SET user_id = ?1, " +
            "structure = (SELECT s.structure_name FROM structures s WHERE s.structure_name = ?2), " +
            "profile = (SELECT pr.profile_type FROM profiles pr WHERE pr.profile_type = ?3) " +
            "WHERE participant_id = ?4",
            nativeQuery = true)
    void updateParticipant(Long userId, String structure, String profile, Long participantId);

}