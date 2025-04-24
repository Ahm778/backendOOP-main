package com.ekher.projet.demo.mappers;

import com.ekher.projet.demo.dto.DomainDto;
import com.ekher.projet.demo.entities.Domain;

public class DomainMapper {
    public static Domain toEntity(DomainDto domainDto) {
        return Domain.builder()
                .domainId(domainDto.getDomainId())
                .domainName(domainDto.getDomainName())
                .build();

    }
    public static DomainDto toDto(Domain domain) {
        return DomainDto.builder()
                .domainId(domain.getDomainId())
                .domainName(domain.getDomainName())
                .build();
    }
}
