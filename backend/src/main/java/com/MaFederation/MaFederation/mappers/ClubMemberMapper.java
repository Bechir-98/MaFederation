package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;
import com.MaFederation.MaFederation.dto.ClubMemberDTO;
import com.MaFederation.MaFederation.model.ClubMember;

@Service
public class ClubMemberMapper {

    public ClubMemberDTO toDto(ClubMember member) {
        if (member == null) return null;

        return new ClubMemberDTO(
            member.getUserId(),
            member.getPosition(),
            member.getClub() != null ? member.getClub().getClubID() : null
        );
    }

    public ClubMember toEntity(ClubMemberDTO dto) {
        if (dto == null) return null;

        ClubMember member = new ClubMember();
        member.setUserId(dto.userId());
        member.setPosition(dto.position());
        // Pour le club, tu dois charger l’entité Club via son ID avant de la setter ici
        // Exemple (hors mapper): member.setClub(clubRepository.findById(dto.clubId()).orElse(null));

        return member;
    }
}
