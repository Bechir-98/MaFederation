package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.model.Administration;
import org.springframework.stereotype.Component;

@Component
public class AdministrationMapper {

    public ResponceAdministrationDTO toDto(Administration admin) {
        if (admin == null) return null;

        ResponceAdministrationDTO dto = new ResponceAdministrationDTO();

        dto.setId(admin.getId());
        dto.setEmail(admin.getEmail());
        dto.setFirstName(admin.getFirstName());
        dto.setLastName(admin.getLastName());
        dto.setDateOfBirth(admin.getDateOfBirth());
        dto.setGender(admin.getGender());
        dto.setPhoneNumber(admin.getPhoneNumber());
        dto.setAddress(admin.getAddress());
        dto.setNationalID(admin.getNationalID());
        dto.setNationality(admin.getNationality());

        if (admin.getClub() != null) {
            dto.setClubId(admin.getClub().getId());
        }

        dto.setProfilePicture(admin.getProfilePicture());

        return dto;
    }

    public Administration toEntity(PostAdminstrationDTO dto) {
        if (dto == null) return null;

        Administration admin = new Administration();

        admin.setEmail(dto.getEmail());
        admin.setPasswordHash(dto.getPasswordHash());
        admin.setProfilePicture(dto.getProfilePicture());
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setDateOfBirth(dto.getDateOfBirth());
        admin.setGender(dto.getGender());
        admin.setPhoneNumber(dto.getPhoneNumber());
        admin.setAddress(dto.getAddress());
        admin.setNationalID(dto.getNationalID());
        admin.setNationality(dto.getNationality());

        // No club set here!

        return admin;
    }
}
