package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.Gender;
import com.ekher.projet.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    int countByGender(Gender gender);
    Optional<User> findByEmail(String email);
}
