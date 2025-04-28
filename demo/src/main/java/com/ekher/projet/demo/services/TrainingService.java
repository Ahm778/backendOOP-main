package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.TrainingDto;
import com.ekher.projet.demo.entities.Training;
import com.ekher.projet.demo.entities.Trainer;
import com.ekher.projet.demo.mappers.TrainingMapper;
import com.ekher.projet.demo.mappers.DomainMapper;
import com.ekher.projet.demo.repositories.TrainingRepository;
import com.ekher.projet.demo.repositories.TrainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;

    @Value("${spring.application.offset}")
    private int offset;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository,
                           TrainerRepository trainerRepository) {
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
    }

    public List<TrainingDto> getAllTrainings(int page) {
        return trainingRepository.findAll(PageRequest.of(page, offset))
                .stream()
                .map(TrainingMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TrainingDto> getTrainingById(String id) {
        return trainingRepository.findById(id)
                .map(TrainingMapper::toDto);
    }

    @Transactional
    public Optional<TrainingDto> createTraining(TrainingDto trainingDto, Long trainerId) {
        return trainerRepository.findById(trainerId)
                .map(trainer -> {
                    Training newTraining = Training.builder()
                            .domain(DomainMapper.toEntity(trainingDto.getDomain()))
                            .trainingId(trainingDto.getTrainingId())
                            .title(trainingDto.getTitle())
                            .description(trainingDto.getDescription())
                            .startDate(trainingDto.getStartDate())
                            .endDate(trainingDto.getEndDate())
                            .type(trainingDto.getType())
                            .startTime(trainingDto.getStartTime())
                            .price(trainingDto.getPrice())
                            .endTime(trainingDto.getEndTime())
                            .trainer(trainer)
                            .build();
                    Training savedTraining = trainingRepository.save(newTraining);
                    return TrainingMapper.toDto(savedTraining);
                });
    }

    public List<TrainingDto> getTrainingsByTrainerId(Long trainerId) {
        return trainingRepository.getTrainingsByTrainerId(trainerId)
                .stream()
                .map(TrainingMapper::toDto)
                .toList();
    }

    public boolean deleteTrainingById(String id) {
        if (id == null || !trainingRepository.existsById(id)) {
            return false;
        }
        trainingRepository.deleteById(id);
        return true;
    }
}