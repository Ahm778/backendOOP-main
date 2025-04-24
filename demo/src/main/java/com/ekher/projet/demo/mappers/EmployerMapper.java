package com.ekher.projet.demo.mappers;

import com.ekher.projet.demo.dto.EmployerDto;
import com.ekher.projet.demo.entities.Employer;

public class EmployerMapper {
    public static EmployerDto toDto(Employer employer) {
        return EmployerDto.builder()
                .id(employer.getId())
                .employerName(employer.getEmployerName())
                .build();
    }
    public static Employer toEntity(EmployerDto dto) {
        return Employer.builder()
                .id(dto.getId())
                .employerName(dto.getEmployerName())
                .build();
    }
}