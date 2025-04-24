package com.ekher.projet.demo.repositories;

import com.ekher.projet.demo.entities.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {
    @Query(value = "SELECT COUNT(*)>0 FROM structures WHERE structure_name=?1 ",nativeQuery = true)
    boolean existsByStructureName(String structureName);
}
