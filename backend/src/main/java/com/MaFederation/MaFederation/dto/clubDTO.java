package com.MaFederation.MaFederation.dto;

import java.util.List;

public record ClubDTO(
    Integer clubId,
    String name,
    String location,
    Integer foundedYear,
    String contactEmail,
    String contactPhone,
    String bankAccount,
    String bankName,
    List<Integer> categoryIds,         // Changed from List<CategoryDTO> ✅
    List<Integer> memberIds,
    ClubFileDTO files
) {}
