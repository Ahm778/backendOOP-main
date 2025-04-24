package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.DomainDto;
import com.ekher.projet.demo.entities.Domain;
//import com.example.CenterManagement.exceptions.trainings.DomainNotFoundException;
import com.ekher.projet.demo.mappers.DomainMapper;
import com.ekher.projet.demo.repositories.DomainRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;


@Service
public class DomainService {
    private final DomainRepository domainRepository;
    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }
    public List<DomainDto> getAllDomains() {
        List<Domain> domains = domainRepository.findAll();
        return domains.stream().map(DomainMapper::toDto).collect(Collectors.toList());
    }
    public DomainDto getDomain(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Domain id cannot be null");
        }
        return DomainMapper.toDto(domainRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Domain with id " + id + " not found."))
        );
    }
    public DomainDto createDomain(DomainDto domainDto) {
        if(domainDto == null) {
            throw new IllegalArgumentException("Domain cannot be null");
        }
        Domain domain = DomainMapper.toEntity(domainDto);
        domain = domainRepository.save(domain);
        return DomainMapper.toDto(domain);
    }
    @Transactional
    public void deleteDomain(Long id) {
        if(!domainRepository.existsById(id)) {
            throw new NoSuchElementException("Domain with id " + id + " not found.");
        }
        domainRepository.deleteById(id);
    }

}

