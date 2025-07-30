package com.MaFederation.MaFederation.mappers;


import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.Club.ClubFileDTO;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubFile;


@Service
public class ClubFileMapper {
public ClubFileDTO toDto(ClubFile clubFile) {
    if (clubFile == null) return null;

    return new ClubFileDTO(
        clubFile.getId(),
        clubFile.getType(),
        clubFile.getClub() != null ? clubFile.getClub().getClubId() : null
    );
}

public ClubFile toEntity(ClubFileDTO dto) {
    if (dto == null) return null;

    ClubFile clubFile = new ClubFile();
    // Do NOT set ID here, it's auto-generated
    clubFile.setType(dto.getType());

    if (dto.getClubId() != null) {
        Club club = new Club();
        club.setClubId(dto.getClubId());
        clubFile.setClub(club);
    }

    return clubFile;
}


}