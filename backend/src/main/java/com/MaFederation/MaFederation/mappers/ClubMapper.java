package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;
import com.MaFederation.MaFederation.dto.ClubDTO;
import com.MaFederation.MaFederation.model.ClubRepresentation;

@Service
public class ClubMapper {

    public ClubDTO toDto(ClubRepresentation club) {
        return new ClubDTO(
            club.getName(),
            club.getLocation(),
            club.getFoundedYear(),   
            club.getContactEmail(),
            club.getContactPhone(),
            club.getBankAccount(),
            club.getBankName()
        );
    }

  public ClubRepresentation fromDto(ClubDTO clubdto) {
    ClubRepresentation club = new ClubRepresentation();
    club.setName(clubdto.name());
    club.setLocation(clubdto.location());
    club.setFoundedYear(clubdto.foundedYear());
    club.setContactEmail(clubdto.contactEmail());
    club.setContactPhone(clubdto.contactPhone());
    club.setBankAccount(clubdto.bankAccount());
    club.setBankName(clubdto.bankName());

    return club;
}


    }
