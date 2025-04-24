package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.Profile;
import com.ekher.projet.demo.entities.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query(value = "SELECT COUNT(*)>0 FROM profiles WHERE profile_type=?1 ",nativeQuery = true)
    boolean existsByProfileType(String profileType);
}
