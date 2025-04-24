package com.ekher.projet.demo.mappers;

import com.ekher.projet.demo.dto.ProfileDto;
import com.ekher.projet.demo.entities.Profile;

public class ProfileMapper {
    public static Profile toEntity(ProfileDto profile) {
        return Profile.builder()
                .profileId(profile.getProfileId())
                .profileType(profile.getProfileType())
                .build();
    }
    public static ProfileDto toDto(Profile profile) {
        return ProfileDto.builder()
                .profileId(profile.getProfileId())
                .profileType(profile.getProfileType())
                .build();
    }
}
