package com.MaFederation.MaFederation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDTO(
    Integer userID,
    Integer clubID,
    String username,
    String email,
    String firstName,
    String lastName,
    LocalDate dateOfBirth,
    String gender,
    String phoneNumber,
    String address,
    String nationalID,
    String nationality,
    Integer profilePhotoFileID,
    String userType,
    LocalDateTime lastLogin
) {}
