package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.ProfileDto;

import com.ekher.projet.demo.mappers.ProfileMapper;
import com.ekher.projet.demo.repositories.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    final private ProfileRepository profileRepository;
    ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<ProfileDto> getAllProfiles() {
        return profileRepository.findAll().stream().map(ProfileMapper::toDto).collect(Collectors.toList());
    }

    public ProfileDto createProfile(ProfileDto profileDto) {
        return ProfileMapper.toDto(profileRepository.save(ProfileMapper.toEntity(profileDto)));
    }
    @Transactional
    public void deleteProfileById(Long id) {
        if(!profileRepository.existsById(id)) {
            throw new NoSuchElementException("Profile with id "+id+" does not exist");
        }
        profileRepository.deleteById(id);
    }

}