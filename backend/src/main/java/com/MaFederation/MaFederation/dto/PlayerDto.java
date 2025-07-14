package com.MaFederation.MaFederation.dto;
import com.MaFederation.MaFederation.model.UserRepresentation;
import java.math.BigDecimal;

public record PlayerDto(
    Integer id,

    // Player-specific data
    String position,
    Integer jerseynumber,
    BigDecimal height,
    BigDecimal weight,
    
    // Flattened User info
    UserRepresentation user
 
) {}
