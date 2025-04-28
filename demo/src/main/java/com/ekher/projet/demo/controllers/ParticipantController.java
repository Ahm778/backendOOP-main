package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.ParticipantDto;
import com.ekher.projet.demo.dto.ProfileDto;
import com.ekher.projet.demo.dto.StructureDto;
import com.ekher.projet.demo.dto.UserDto;
import com.ekher.projet.demo.entities.Role;
import com.ekher.projet.demo.models.requestData.ParticipantRequestData;
import com.ekher.projet.demo.services.ParticipantService;
import com.ekher.projet.demo.services.ProfileService;
import com.ekher.projet.demo.services.StructureService;
import com.ekher.projet.demo.utils.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {
    private final ParticipantService participantService;
    private final StructureService structureService;
    private final ProfileService profileService;

    @Autowired
    public ParticipantController(ParticipantService participantService,
                                 StructureService structureService,
                                 ProfileService profileService) {
        this.participantService = participantService;
        this.structureService = structureService;
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<List<ParticipantDto>> getAllParticipants(
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok(participantService.getAllParticipants(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDto> getParticipant(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return participantService.getParticipant(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@RequestBody ParticipantRequestData data) {
        if (data == null || data.getEmail() == null || data.getUsername() == null ||
                data.getStructureId() == null || data.getProfileId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<StructureDto> structure = structureService.getStructureById(data.getStructureId());
        Optional<ProfileDto> profile = profileService.getProfileById(data.getProfileId());

        if (structure.isEmpty() || profile.isEmpty()) {
            return ResponseEntity.badRequest().build();
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
                .structure(structure.get())
                .profile(profile.get())
                .build();

        ParticipantDto createdParticipant = participantService.createParticipant(participantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipant);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<ParticipantDto>> updateParticipant(@PathVariable Long id,
                                                                      @RequestBody ParticipantRequestData data) {
        if (id == null || data == null) {
            return ResponseEntity.badRequest().build();
        }

        return participantService.getParticipant(id)
                .map(existingParticipant -> {
                    UserDto user = updateUserFields(existingParticipant.getUser(), data);

                    StructureDto structure = data.getStructureId() != null
                            ? structureService.getStructureById(data.getStructureId()).orElse(existingParticipant.getStructure())
                            : existingParticipant.getStructure();

                    ProfileDto profile = data.getProfileId() != null
                            ? profileService.getProfileById(data.getProfileId()).orElse(existingParticipant.getProfile())
                            : existingParticipant.getProfile();

                    ParticipantDto updatedParticipant = ParticipantDto.builder()
                            .participantId(existingParticipant.getParticipantId())
                            .user(user)
                            .structure(structure)
                            .profile(profile)
                            .build();

                    return ResponseEntity.ok(participantService.updateParticipant(updatedParticipant));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private UserDto updateUserFields(UserDto existingUser, ParticipantRequestData data) {
        return UserDto.builder()
                .userId(existingUser.getUserId())
                .username(data.getUsername() != null ? data.getUsername() : existingUser.getUsername())
                .email(data.getEmail() != null ? data.getEmail() : existingUser.getEmail())
                .dateOfBirth(data.getDateOfBirth() != null ? data.getDateOfBirth() : existingUser.getDateOfBirth())
                .description(data.getDescription() != null ? data.getDescription() : existingUser.getDescription())
                .password(existingUser.getPassword())
                .role(Role.PARTICIPANT)
                .gender(data.getGender() != null ? data.getGender() : existingUser.getGender())
                .phoneNumber(data.getPhoneNumber() != null ? data.getPhoneNumber() : existingUser.getPhoneNumber())
                .profilePicture(data.getProfilePicture() != null ? data.getProfilePicture() : existingUser.getProfilePicture())
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return participantService.deleteParticipant(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}