package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {
    boolean existsByStructureName(String structureName);
}
