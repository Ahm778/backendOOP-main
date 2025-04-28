package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Gender;
import com.ekher.projet.demo.entities.Role;
import com.ekher.projet.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    int countByGender(Gender gender);
    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role);
}