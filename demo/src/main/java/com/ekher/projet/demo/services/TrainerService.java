package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.TrainerDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Trainer;
import com.ekher.projet.demo.entities.User;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Transactional
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;

    @Value("${spring.application.offset}")
    private int offset;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository, UserRepository userRepository, EmployerRepository employerRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
    }

    public List<TrainerDto> getAllTrainers(int page) {
        return trainerRepository.findAll(PageRequest.of(page,offset))
                .stream()
                .map(TrainerMapper::toLightDto)
                .collect(Collectors.toList());
    }

    public TrainerDto getTrainer(Long id) {
        return trainerRepository.findById(id)
                .map(TrainerMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found"));
    }

    @Transactional
    public TrainerDto createTrainer(TrainerDto trainerDto) {
        if(!employerRepository.existsByEmployerName(trainerDto.getEmployerName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Employer with name: " + trainerDto.getEmployerName() + " does not exist");
        }

        User savedUser = userRepository.save(UserMapper.toEntity(trainerDto.getUser()));
        Trainer trainer = Trainer.builder()
                .trainerId(savedUser.getUserId())
                .user(savedUser)
                .trainerType(trainerDto.getTrainerType())
                .employerName(trainerDto.getEmployerName())
                .build();
        Trainer savedTrainer = trainerRepository.save(trainer);
        return TrainerMapper.toDto(savedTrainer);
    }

    public TrainerDto updateTrainer(TrainerDto trainerDto, UserDto userDto) {
        if(!trainerRepository.existsById(trainerDto.getTrainerId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer id not found");
        }
        if(!employerRepository.existsByEmployerName(trainerDto.getEmployerName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Employer with name: " + trainerDto.getEmployerName() + " does not exist");
        }

        User savedUser = userRepository.save(UserMapper.toEntity(userDto));
        Trainer trainer = Trainer.builder()
                .trainerId(trainerDto.getTrainerId())
                .user(savedUser)
                .trainerType(trainerDto.getTrainerType())
                .employerName(trainerDto.getEmployerName())
                .build();
        Trainer savedTrainer = trainerRepository.save(trainer);
        return TrainerMapper.toDto(savedTrainer);
    }

    public void deleteTrainer(Long id) {
        if(!trainerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer id not found");
        }
        trainerRepository.deleteById(id);
    }
}