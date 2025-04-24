package com.ekher.projet.demo.services;

import com.ekher.projet.demo.dto.TrainingDto;
import com.ekher.projet.demo.dto.TrainingParticipantDto;
import com.ekher.projet.demo.dto.ParticipantDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Training;
import com.ekher.projet.demo.entities.TrainingParticipant;
import com.ekher.projet.demo.entities.TrainingParticipantsId;
import com.ekher.projet.demo.entities.Participant;
import com.ekher.projet.demo.entities.User;
import com.ekher.projet.demo.mappers.TrainingParticipantMapper;
import com.ekher.projet.demo.repositories.TrainingParticipantsRepository;
import com.ekher.projet.demo.repositories.TrainingRepository;
import com.ekher.projet.demo.repositories.ParticipantRepository;
import com.ekher.projet.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TrainingEnrollmentService {
    private final TrainingParticipantsRepository trainingParticipantsRepository;
    private final TrainingRepository trainingRepository;
    private final UserRepository participantRepository;

    @Autowired
    public TrainingEnrollmentService(TrainingParticipantsRepository trainingParticipantsRepository,
                                     TrainingRepository trainingRepository,
                                     UserRepository participantRepository) {
        this.trainingParticipantsRepository = trainingParticipantsRepository;
        this.trainingRepository = trainingRepository;
        this.participantRepository = participantRepository;
    }

    public TrainingParticipantDto createTrainingEnrollment(String trainingId, Long userId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Training with id: " + trainingId + " not found"));

        User participant = participantRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User id not found"));

        if (trainingParticipantsRepository.existsById(new TrainingParticipantsId(trainingId, userId))) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Participant with id: " + userId + " already enrolled to this training");
        }

        TrainingParticipant enrollment = TrainingParticipant.builder()
                .id(new TrainingParticipantsId(trainingId, userId))
                .training(training)
                .participant(participant)
                .build();

        return TrainingParticipantMapper.toDto(trainingParticipantsRepository.save(enrollment));
    }

    public List<UserDto> getEnrollmentParticipants(String trainingId) {
        return trainingParticipantsRepository.findAllByTrainingId(trainingId).stream()
                .map(enrollment -> TrainingParticipantMapper.toDto(enrollment).getParticipant())
                .collect(Collectors.toList());
    }

    public List<TrainingDto> getParticipantsEnrollment(Long user_id) {
        return trainingParticipantsRepository.findTrainingsForParticipant(user_id).stream()
                .map(enrollment -> TrainingParticipantMapper.toDto(enrollment).getTraining())
                .collect(Collectors.toList());
    }

    public void cancelTrainingEnrollment(Long user_id, String id) {
        if (!trainingParticipantsRepository.existsById(new TrainingParticipantsId(id, user_id))) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User with id: " + user_id + " not enrolled to training with id: " + id);
        }
        this.trainingParticipantsRepository.deleteById(new TrainingParticipantsId(id, user_id));
    }
}