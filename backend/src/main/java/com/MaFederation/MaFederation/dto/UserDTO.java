package com.MaFederation.MaFederation.dto;

import java.time.LocalDate;

public record UserDTO(
    Integer userId,
    String username,
    String email,
    String firstName,
    String lastName,
    LocalDate dateOfBirth,
    String gender,
    String phoneNumber,
    String address,
    String nationalID,
    String nationality
) {}
