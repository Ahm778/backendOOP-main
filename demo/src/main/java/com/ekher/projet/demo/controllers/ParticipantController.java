package com.ekher.projet.demo.controllers;
//import com.ekher.projet.demo.annotations.users.CheckInCache;
import com.ekher.projet.demo.dto.ParticipantDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Role;

import com.ekher.projet.demo.models.requestData.ParticipantRequestData;
import com.ekher.projet.demo.services.EmailService;
import com.ekher.projet.demo.services.ParticipantService;
import com.ekher.projet.demo.utils.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/api/v1/participants")
public class ParticipantController {
    private final ParticipantService participantService;
    private final EmailService emailService;
    @Autowired
    public ParticipantController(ParticipantService participantService,EmailService emailService) {
        this.participantService = participantService;
        this.emailService = emailService;
    }



    @GetMapping
    public ResponseEntity<List<ParticipantDto>> getAllParticipants(@RequestParam(required = false, defaultValue = "0") Integer page) {
        List<ParticipantDto> participants = participantService.getAllParticipants(page);
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }



    //@CheckInCache(type = "participant")
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDto> getParticipant(@PathVariable Long id) {
        if (id == null) {


            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided participant id is nul");
        }
        ParticipantDto participant = participantService.getParticipant(id);
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@RequestBody ParticipantRequestData data) {
        if (data == null || data.getEmail() == null || data.getUsername() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided participant id is nul");
        }
        String password = RandomPasswordGenerator.generateRandomPassword();
        UserDto user = UserDto.builder()
                .email(data.getEmail())
                .username(data.getUsername())
                .description(data.getDescription())
                .dateOfBirth(data.getDateOfBirth())
                .phoneNumber(data.getPhoneNumber())
                .role(Role.PARTICIPANT)
                .password(password)
                .gender(data.getGender())
                .profilePicture(data.getProfilePicture())
                .build();
        ParticipantDto participantDto = ParticipantDto.builder()
                .user(user)
                .structure(data.getStructure())
                .profile(data.getProfile())
                .build();
        ParticipantDto participant = participantService.createParticipant(participantDto);
        emailService.sendSimpleEmail(user.getEmail(), "An account with this email have been created",password);
        return new ResponseEntity<>(participant, HttpStatus.CREATED);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<ParticipantDto> updateParticipant(@PathVariable Long id, @RequestBody ParticipantRequestData data) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided participantId is null");
        }

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided participantId is null");
        }

        ParticipantDto participant = participantService.getParticipant(id);
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided participantId is null");
        }

        UserDto oldUser = participant.getUser();
        UserDto newUser = UserDto.builder()
                .username(data.getUsername() != null ? data.getUsername() : oldUser.getUsername())
                .email(data.getEmail() != null ? data.getEmail() : oldUser.getEmail())
                .dateOfBirth(data.getDateOfBirth() != null ? data.getDateOfBirth() : oldUser.getDateOfBirth())
                .description(data.getDescription() != null ? data.getDescription() : oldUser.getDescription())
                .password(oldUser.getPassword())
                .role(Role.PARTICIPANT)
                .userId(participant.getUser().getUserId())
                .gender(data.getGender() != null ? data.getGender() : oldUser.getGender())
                .phoneNumber(data.getPhoneNumber() != null ? data.getPhoneNumber() : oldUser.getPhoneNumber())

                .profilePicture(data.getProfilePicture() != null ? data.getProfilePicture() : oldUser.getProfilePicture())
                .build();
        ParticipantDto newParticipant = ParticipantDto.builder()
                .participantId(participant.getParticipantId())
                .user(newUser)
                .structure(data.getStructure() != null ? data.getStructure() : participant.getStructure())
                .profile(data.getProfile() != null ? data.getProfile() : participant.getProfile())
                .build();
        ParticipantDto participantDto = participantService.updateParticipant(newParticipant);
        return new ResponseEntity<>(participantDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided participantId is null");
        }

        participantService.deleteParticipant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}