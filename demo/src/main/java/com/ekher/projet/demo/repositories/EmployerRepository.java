package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    @Query(value = "SELECT COUNT(*)>0 FROM  employers e WHERE e.employer_name= ?1",nativeQuery = true)

    boolean existsByEmployerName(String employerName);

}