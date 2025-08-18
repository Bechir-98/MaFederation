package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.ClubVerificationRequestDTO;
import com.MaFederation.MaFederation.model.ClubVerificationRequest;

public class ClubVerificationRequestMapper {

    public static ClubVerificationRequestDTO toDTO(ClubVerificationRequest entity) {
        if (entity == null) return null;

        return new ClubVerificationRequestDTO(
            entity.getId(),                               // ✅ return id
            entity.getClub() != null ? entity.getClub().getId() : null,
            entity.getClub() != null ? entity.getClub().getName() : null,
            entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null
        );
    }
}
