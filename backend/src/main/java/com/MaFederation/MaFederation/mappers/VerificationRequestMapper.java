package com.MaFederation.MaFederation.mappers;


import com.MaFederation.MaFederation.model.UserVerificationRequest;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.dto.VerificationRequestResponseDTO.VerificationRequestResponseDTO;
import com.MaFederation.MaFederation.model.Club;

public class VerificationRequestMapper {

    // ✅ Entity → DTO
    public static VerificationRequestResponseDTO toDto(UserVerificationRequest request) {
        if (request == null) {
            return null;
        }

        VerificationRequestResponseDTO dto = new VerificationRequestResponseDTO();
        dto.setId(request.getId());

        if (request.getUser() != null) {
            dto.setUserId(request.getUser().getId());
            dto.setUserName(request.getUser().getFirstName() + " " + request.getUser().getLastName());
        }

        if (request.getClub() != null) {
            dto.setClubId(request.getClub().getId());
            dto.setClubName(request.getClub().getName());
        }
        dto.setCreatedAt(request.getCreatedAt());
        dto.setTargetType(request.getTargetType());

        return dto;
    }

    // ✅ DTO → Entity (only IDs are set, so you must ensure persistence later)
    public static UserVerificationRequest toEntity(VerificationRequestResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        UserVerificationRequest request = new UserVerificationRequest();

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            request.setUser(user);
        }

        if (dto.getClubId() != null) {
            Club club = new Club();
            club.setId(dto.getClubId());
            request.setClub(club);
        }

        request.setTargetType(dto.getTargetType());

        return request;
    }
}
