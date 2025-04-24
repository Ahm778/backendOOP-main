package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.TrainingDto;
import com.ekher.projet.demo.entities.Training;
import com.ekher.projet.demo.entities.Trainer;
import com.ekher.projet.demo.mappers.TrainingMapper;
import com.ekher.projet.demo.repositories.DomainRepository;
import com.ekher.projet.demo.repositories.TrainingRepository;
import com.ekher.projet.demo.repositories.TrainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final DomainRepository domainRepository;

    @Value("${spring.application.offset}")
    private int offset;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository,
                           TrainerRepository trainerRepository,
                           DomainRepository domainRepository) {
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
        this.domainRepository = domainRepository;
    }

    public List<TrainingDto> getAllTrainings(int page) {
        return trainingRepository.findAll(PageRequest.of(page, offset))
                .stream()
                .map(TrainingMapper::toDto)
                .collect(Collectors.toList());
    }

    public TrainingDto getTrainingById(String id) {
        return trainingRepository.findById(id)
                .map(TrainingMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Training with id " + id + " not found"));
    }

    @Transactional
    public TrainingDto createTraining(TrainingDto trainingDto, Long trainerId) {
        if(domainRepository.getDomainByDomainName(trainingDto.getDomainName()) == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Domain name " + trainingDto.getDomainName() + " does not exist");
        }

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Trainer not found with id: " + trainerId));

        Training newTraining = Training.builder()
                .domainName(trainingDto.getDomainName())
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
    }

    public void deleteTrainingById(String id) {
        if (!trainingRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Training not found with ID: " + id);
        }
        trainingRepository.deleteById(id);
    }

    // Ajoutez cette méthode pour résoudre l'erreur dans le contrôleur
    public TrainingDto updateTraining(TrainingDto trainingDto, Long trainerId) {
        Training training = trainingRepository.findById(trainingDto.getTrainingId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Training not found with ID: " + trainingDto.getTrainingId()));

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Trainer not found with ID: " + trainerId));

        // Mise à jour des champs
        training.setTitle(trainingDto.getTitle());
        training.setDescription(trainingDto.getDescription());
        training.setStartDate(trainingDto.getStartDate());
        training.setEndDate(trainingDto.getEndDate());
        training.setType(trainingDto.getType());
        training.setStartTime(trainingDto.getStartTime());
        training.setEndTime(trainingDto.getEndTime());
        training.setPrice(trainingDto.getPrice());
        training.setDomainName(trainingDto.getDomainName());
        training.setTrainer(trainer);

        Training updatedTraining = trainingRepository.save(training);
        return TrainingMapper.toDto(updatedTraining);
    }
}