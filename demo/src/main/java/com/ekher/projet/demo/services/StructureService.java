package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.StructureDto;
import com.ekher.projet.demo.mappers.StructureMapper;
import com.ekher.projet.demo.repositories.StructureRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StructureService {
    private final StructureRepository structureRepository;

    public StructureService(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }

    public List<StructureDto> getAllStructures() {
        return structureRepository.findAll()
                .stream()
                .map(StructureMapper::toDto)
                .collect(Collectors.toList());
    }

    public StructureDto createStructure(StructureDto structureDto) {
        return StructureMapper.toDto(structureRepository.save(StructureMapper.toEntity(structureDto)));
    }

    @Transactional
    public boolean deleteStructure(Long id) {
        if (id == null || !structureRepository.existsById(id)) {
            return false;
        }
        structureRepository.deleteById(id);
        return true;
    }

    public Optional<StructureDto> getStructureById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return structureRepository.findById(id)
                .map(StructureMapper::toDto);
    }
}