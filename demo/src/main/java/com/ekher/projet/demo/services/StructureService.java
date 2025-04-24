package com.ekher.projet.demo.services;



import com.ekher.projet.demo.dto.StructureDto;
//import com.example.CenterManagement.exceptions.users.StructureNotFoundException;
import com.ekher.projet.demo.mappers.StructureMapper;
import com.ekher.projet.demo.repositories.StructureRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StructureService {
    private final StructureRepository structureRepository;
    public StructureService(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }
    public List<StructureDto> getAllStructures() {
        return structureRepository.findAll().stream().map(StructureMapper::toDto).collect(Collectors.toList());
    }
    public StructureDto createStructure(StructureDto structureDto) {
        return StructureMapper.toDto(structureRepository.save(StructureMapper.toEntity(structureDto)));
    }
    @Transactional
    public void deleteStructure(Long id) {
        if (!structureRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Structure with id: "+id+" does not exist");
        }
        structureRepository.deleteById(id);
    }
}
