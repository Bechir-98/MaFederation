package com.MaFederation.MaFederation.dto;

import java.math.BigDecimal;

public record PlayerDto(
    Integer id,

    // Player-specific data
    String position,
    Integer jerseynumber,
    BigDecimal height,
    BigDecimal weight,

    // Flattened User info
    Integer userID,
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    String gender,
    String nationality,
    String address
) {}
