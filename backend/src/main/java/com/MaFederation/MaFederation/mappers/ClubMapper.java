package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;
import com.MaFederation.MaFederation.dto.ClubDTO;
import com.MaFederation.MaFederation.model.Club;

@Service
public class ClubMapper {

    public ClubDTO toDto(Club club) {
        if (club == null) {
            return null;
        }
        return new ClubDTO(
            club.getClubID(),
            club.getName(),
            club.getLocation(),
            club.getFoundedYear(),
            club.getContactEmail(),
            club.getContactPhone(),
            club.getLogoUrl(),
            club.getBankAccount(),
            club.getBankName(),
            club.getIsMember()
        );
    }

    public Club fromDto(ClubDTO clubDto) {
        if (clubDto == null) {
            return null;
        }

        Club club = new Club();
        club.setClubID(clubDto.clubId());
        club.setName(clubDto.name());
        club.setLocation(clubDto.location());
        club.setFoundedYear(clubDto.foundedYear());
        club.setContactEmail(clubDto.contactEmail());
        club.setContactPhone(clubDto.contactPhone());
        club.setBankAccount(clubDto.bankAccount());
        club.setBankName(clubDto.bankName());
        club.setLogoUrl(clubDto.logoUrl());
        club.setIsMember(clubDto.isMember());

        return club;
    }
}
