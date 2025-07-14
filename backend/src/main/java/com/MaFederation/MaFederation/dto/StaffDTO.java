package com.MaFederation.MaFederation.dto;

import java.util.List;

public record StaffDTO(
    Integer userId,
    String specialty,
    List<Integer> categoryIds
) {}
