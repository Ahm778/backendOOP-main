package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.ParticipantDto;
import com.ekher.projet.demo.entities.Participant;
import com.ekher.projet.demo.entities.User;
import com.ekher.projet.demo.mappers.ParticipantMapper;
import com.ekher.projet.demo.mappers.UserMapper;
import com.ekher.projet.demo.repositories.ParticipantRepository;
import com.ekher.projet.demo.repositories.ProfileRepository;
import com.ekher.projet.demo.repositories.StructureRepository;
import com.ekher.projet.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Transactional
@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final StructureRepository structureRepository;
    @Value("${spring.application.offset}")
    private int offset;
    @Autowired
    public ParticipantService(ParticipantRepository participantRepository,UserRepository userRepository,ProfileRepository profileRepository,StructureRepository structureRepository) {
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.structureRepository = structureRepository;
    }

    public ParticipantDto  createParticipant(ParticipantDto participant) {
        if(!profileRepository.existsByProfileType(participant.getProfile())){
            throw new IllegalArgumentException("Profile type "+ participant.getProfile()+" not supported");
        }
        if(!structureRepository.existsByStructureName(participant.getStructure())){
            throw new IllegalArgumentException("Structure "+participant.getStructure() +" name not supported");
        }
        User user=userRepository.save(UserMapper.toEntity(participant.getUser()));
        Participant newParticipant = Participant.builder()
                .participantId(user.getUserId())
                .user(user)
                .profile(participant.getProfile())
                .structure(participant.getStructure())
                .build();
        return  ParticipantMapper.toDto(participantRepository.save(newParticipant));


    }
    public List<ParticipantDto> getAllParticipants(int page) {
        return participantRepository.findAll(PageRequest.of(page,offset )).stream().map(ParticipantMapper::toLightDto).collect(Collectors.toList());
    }
    public ParticipantDto getParticipant(Long id) {
        return ParticipantMapper.toDto(participantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Participant not found")));
    }
    @Transactional
    public ParticipantDto updateParticipant(ParticipantDto participant) {
        if(!profileRepository.existsByProfileType(participant.getProfile())){
            throw new IllegalArgumentException("Profile type "+ participant.getProfile()+" not supported");
        }
        if(!structureRepository.existsByStructureName(participant.getStructure())){
            throw new IllegalArgumentException("Structure "+participant.getStructure() +" name not supported");
        }
        User user = userRepository.save(UserMapper.toEntity(participant.getUser()));
        Participant newParticipant = Participant.builder()
                .participantId(participant.getParticipantId())
                .user(user)
                .profile(participant.getProfile())
                .structure(participant.getStructure())
                .build();

        return  ParticipantMapper.toDto(participantRepository.save(newParticipant));

    }

    public void deleteParticipant(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new NoSuchElementException("Participant not found with ID: " + id);
        }
        participantRepository.deleteById(id);
    }

}