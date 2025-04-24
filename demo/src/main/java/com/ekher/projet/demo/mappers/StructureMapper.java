package com.ekher.projet.demo.mappers;

import com.ekher.projet.demo.dto.StructureDto;
import com.ekher.projet.demo.entities.Structure;

public class StructureMapper {
    static public Structure toEntity(StructureDto structureDto) {
        return Structure.builder()
                .structureId(structureDto.getStructureId())
                .structureName(structureDto.getStructureName())
                .build();
    }
    public static StructureDto toDto(Structure structure) {
        return StructureDto.builder()
                .structureId(structure.getStructureId())
                .structureName(structure.getStructureName())
                .build();
    }
}