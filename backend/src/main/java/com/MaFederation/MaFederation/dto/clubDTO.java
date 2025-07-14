package com.MaFederation.MaFederation.dto;

public record ClubDTO(
    Integer clubId,
    String name,
    String location,
    Integer foundedYear,
    String contactEmail,
    String contactPhone,
    String logoUrl,
    String bankAccount,
    String bankName,
    Boolean isMember
) {}
