package com.ekher.projet.demo.services;

import com.ekher.projet.demo.entities.Gender;
import com.ekher.projet.demo.entities.TrainerType;
import com.ekher.projet.demo.models.dashboardData.OtherDetails;
import com.ekher.projet.demo.models.dashboardData.*;
import com.ekher.projet.demo.repositories.TrainingParticipantsRepository;
import com.ekher.projet.demo.repositories.TrainingRepository;
import com.ekher.projet.demo.repositories.ParticipantRepository;
import com.ekher.projet.demo.repositories.TrainerRepository;
import com.ekher.projet.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Transactional
@Service
public class DashboardService {
    private final   ParticipantRepository participantRepository;
    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingParticipantsRepository trainingParticipantsRepository;
    private final UserRepository userRepository;
    @Autowired
    public DashboardService(ParticipantRepository participantRepository, TrainingRepository trainingRepository, TrainerRepository trainerRepository,
                            TrainingParticipantsRepository trainingParticipantsRepository, UserRepository userRepository) {
        this.participantRepository = participantRepository;
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
        this.trainingParticipantsRepository = trainingParticipantsRepository;
        this.userRepository = userRepository;
    }

    public OtherDetails getOtherDetails() {
        int nbOfFemale =userRepository.countByGender(Gender.FEMALE);
        int nbOfMale =userRepository.countByGender(Gender.MALE);
        return OtherDetails.builder().nbOfMale(nbOfMale).nbOfFemale(nbOfFemale).build();
    }
    public TrainingsDetails getTrainingsDetails(){
        long nbTrainings=trainingRepository.count();
        AtomicReference<Long> income= new AtomicReference<>(0L);
        trainingParticipantsRepository.getAllTrainingIds().forEach(trainingId -> {
            income.set(income.get() + trainingParticipantsRepository.getIncomePerTraining(trainingId));
        });
        List<DomainCount>  trainingsPerDomain=trainingRepository.getTrainingsPerDomain();
        return TrainingsDetails.builder().nbTrainings(nbTrainings).trainingsPerDomain(trainingsPerDomain).totalIncome(income.get()).build();

    }
    public TrainersDetails getTrainersDetails(){
        long nbTrainers=trainerRepository.count();
        int internalTrainersCount= trainerRepository.countByTrainerType(TrainerType.INTERNAL);
        int externalTrainersCount= trainerRepository.countByTrainerType(TrainerType.EXTERNAL);
        List<TopUsers> topTrainers=trainingParticipantsRepository.getTopTrainers();
        return TrainersDetails.builder()
                .externalTrainersCount(externalTrainersCount).internalTrainersCount(internalTrainersCount)
                .topTrainers(topTrainers).nbTrainers(nbTrainers).build();
    }
    public ParticipantsDetails getParticipantsDetails(){
        long nbParticipants=participantRepository.count();
        List<TopUsers> topParticipants=trainingParticipantsRepository.getTopParticipants();
        List<TopUsers> topParticipantsWithDomains=trainingParticipantsRepository.getTopParticipantsWithDomains();
        return ParticipantsDetails.builder().nbParticipants(nbParticipants).topParticipants(topParticipants).topParticipantsWithDomains(topParticipantsWithDomains).build();
    }


}