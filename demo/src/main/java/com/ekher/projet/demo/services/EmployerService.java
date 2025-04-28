package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.EmployerDto;
import com.ekher.projet.demo.mappers.EmployerMapper;
import com.ekher.projet.demo.repositories.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;

    @Autowired
    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public List<EmployerDto> getAllEmployers() {
        return employerRepository.findAll()
                .stream()
                .map(EmployerMapper::toDto)
                .collect(Collectors.toList());
    }

    public EmployerDto createEmployer(EmployerDto employerDto) {
        return EmployerMapper.toDto(employerRepository.save(EmployerMapper.toEntity(employerDto)));
    }

    public boolean deleteEmployer(long id) {
        if (!employerRepository.existsById(id)) {
            return false;
        }
        employerRepository.deleteById(id);
        return true;
    }

    public Optional<EmployerDto> getEmployerById(long id) {
        return employerRepository.findById(id)
                .map(EmployerMapper::toDto);
    }
}