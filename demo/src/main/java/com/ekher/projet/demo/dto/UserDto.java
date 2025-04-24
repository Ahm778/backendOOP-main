package com.ekher.projet.demo.dto;


import com.ekher.projet.demo.entities.Gender;
import com.ekher.projet.demo.entities.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @JsonIgnore
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;
    @NotNull(message = "Role cannot be null")
    private Role role;
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private Gender gender;
    private String profilePicture;
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
}
