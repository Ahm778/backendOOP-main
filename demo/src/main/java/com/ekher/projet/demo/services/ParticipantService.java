package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.ParticipantDto;
import com.ekher.projet.demo.entities.Participant;
import com.ekher.projet.demo.entities.User;
import com.ekher.projet.demo.mappers.ParticipantMapper;
import com.ekher.projet.demo.mappers.ProfileMapper;
import com.ekher.projet.demo.mappers.StructureMapper;
import com.ekher.projet.demo.mappers.UserMapper;
import com.ekher.projet.demo.repositories.ParticipantRepository;
import com.ekher.projet.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Value("${spring.application.offset}")
    private int offset;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository,
                              UserRepository userRepository) {
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
    }

    public ParticipantDto createParticipant(ParticipantDto participantDto) {
        User user = userRepository.save(UserMapper.toEntity(participantDto.getUser()));
        Participant newParticipant = Participant.builder()
                .participantId(user.getUserId())
                .user(user)
                .profile(ProfileMapper.toEntity(participantDto.getProfile()))
                .structure(StructureMapper.toEntity(participantDto.getStructure()))
                .build();
        return ParticipantMapper.toDto(participantRepository.save(newParticipant));
    }

    public List<ParticipantDto> getAllParticipants(int page) {
        return participantRepository.findAll(PageRequest.of(page, offset))
                .stream()
                .map(ParticipantMapper::toLightDto)
                .collect(Collectors.toList());
    }

    public Optional<ParticipantDto> getParticipant(Long id) {
        return participantRepository.findById(id)
                .map(ParticipantMapper::toDto);
    }

    @Transactional
    public Optional<ParticipantDto> updateParticipant(ParticipantDto participantDto) {
        if (!participantRepository.existsById(participantDto.getParticipantId())) {
            return Optional.empty();
        }

        User user = userRepository.save(UserMapper.toEntity(participantDto.getUser()));
        Participant updatedParticipant = Participant.builder()
                .participantId(participantDto.getParticipantId())
                .user(user)
                .profile(ProfileMapper.toEntity(participantDto.getProfile()))
                .structure(StructureMapper.toEntity(participantDto.getStructure()))
                .build();

        return Optional.of(ParticipantMapper.toDto(participantRepository.save(updatedParticipant)));
    }

    public boolean deleteParticipant(Long id) {
        if (!participantRepository.existsById(id)) {
            return false;
        }
        participantRepository.deleteById(id);
        return true;
    }
}