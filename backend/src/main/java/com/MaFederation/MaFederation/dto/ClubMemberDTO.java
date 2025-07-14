package com.MaFederation.MaFederation.dto;

public record ClubMemberDTO(
    Integer userId,       // ID hérité de User
    String position,
    Integer clubId       // référence à Club par ID
) {}
