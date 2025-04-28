package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.TrainerDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Trainer;
import com.ekher.projet.demo.entities.User;
import com.ekher.projet.demo.mappers.EmployerMapper;
import com.ekher.projet.demo.mappers.TrainerMapper;
import com.ekher.projet.demo.mappers.UserMapper;
import com.ekher.projet.demo.repositories.EmployerRepository;
import com.ekher.projet.demo.repositories.TrainerRepository;
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
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;

    @Value("${spring.application.offset}")
    private int offset;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository,
                          UserRepository userRepository,
                          EmployerRepository employerRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
    }

    public List<TrainerDto> getAllTrainers(int page) {
        return trainerRepository.findAll(PageRequest.of(page, offset))
                .stream()
                .map(TrainerMapper::toLightDto)
                .collect(Collectors.toList());
    }

    public Optional<TrainerDto> getTrainer(Long id) {
        return trainerRepository.findById(id)
                .map(TrainerMapper::toDto);
    }

    @Transactional
    public TrainerDto createTrainer(TrainerDto trainerDto) {
        User savedUser = userRepository.save(UserMapper.toEntity(trainerDto.getUser()));
        Trainer trainer = Trainer.builder()
                .trainerId(savedUser.getUserId())
                .user(savedUser)
                .trainerType(trainerDto.getTrainerType())
                .employer(EmployerMapper.toEntity(trainerDto.getEmployer()))
                .build();
        Trainer savedTrainer = trainerRepository.save(trainer);
        return TrainerMapper.toDto(savedTrainer);
    }

    public Optional<TrainerDto> updateTrainer(TrainerDto trainerDto, UserDto userDto) {
        if (!trainerRepository.existsById(trainerDto.getTrainerId())) {
            return Optional.empty();
        }

        User savedUser = userRepository.save(UserMapper.toEntity(userDto));
        Trainer trainer = Trainer.builder()
                .trainerId(trainerDto.getTrainerId())
                .user(savedUser)
                .trainerType(trainerDto.getTrainerType())
                .employer(EmployerMapper.toEntity(trainerDto.getEmployer()))
                .build();
        Trainer savedTrainer = trainerRepository.save(trainer);
        return Optional.of(TrainerMapper.toDto(savedTrainer));
    }

    public boolean deleteTrainer(Long id) {
        if (!trainerRepository.existsById(id)) {
            return false;
        }
        trainerRepository.deleteById(id);
        return true;
    }

    public List<TrainerDto> getAllTrainers() {
        return trainerRepository.findAll()
                .stream()
                .map(TrainerMapper::toLightestDto)
                .toList();
    }
}