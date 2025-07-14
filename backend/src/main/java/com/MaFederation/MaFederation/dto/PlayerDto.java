package com.MaFederation.MaFederation.dto;

import java.math.BigDecimal;
import java.util.List;

public record PlayerDTO(
    Integer userId,
    String position,
    Integer jerseyNumber,
    BigDecimal height,
    BigDecimal weight,
    List<Integer> categoryIds    // Liste des IDs des categories associées
) {}
