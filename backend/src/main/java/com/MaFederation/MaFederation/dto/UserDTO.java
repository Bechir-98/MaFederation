package com.MaFederation.MaFederation.dto;

import java.time.LocalDate;

import com.MaFederation.MaFederation.model.ClubRepresentation;

public record UserDTO(
    Integer userID,
    ClubRepresentation club,
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
    String userType
) {}
