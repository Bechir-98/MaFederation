package com.MaFederation.MaFederation.dto.Category;

public record CategoryDTO(
    Integer categoryId,
    String name,
    String description,
    Integer ageMin,
    Integer ageMax
) {}
