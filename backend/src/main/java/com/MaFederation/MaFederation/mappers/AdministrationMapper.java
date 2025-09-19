package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.enums.ClubMemberType;
import com.MaFederation.MaFederation.enums.RoleName;
import com.MaFederation.MaFederation.enums.ValidationStatus;
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
        dto.setPost(admin.getPost());

        if (admin.getClub() != null) {
            dto.setClubId(admin.getClub().getId());
        }

        dto.setProfilePicture(admin.getProfilePicture());

        // Audit fields
        dto.setCreatedAt(admin.getCreatedAt());
        dto.setUpdatedAt(admin.getUpdatedAt());
        dto.setCreatedBy(admin.getCreatedBy());
        dto.setUpdatedBy(admin.getUpdatedBy());
        dto.setValidated(admin.getValidated());
        dto.setValidatedBy(admin.getValidatedBy());
        dto.setValidationDate(admin.getValidationDate());

        return dto;
    }

    public Administration toEntity(PostAdminstrationDTO dto) {
        if (dto == null) return null;

        Administration admin = new Administration();

        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPasswordHash());
        admin.setProfilePicture(dto.getProfilePicture());
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setDateOfBirth(dto.getDateOfBirth());
        admin.setGender(dto.getGender());
        admin.setPhoneNumber(dto.getPhoneNumber());
        admin.setAddress(dto.getAddress());
        admin.setNationalID(dto.getNationalID());
        admin.setNationality(dto.getNationality());
        admin.setPost(dto.getPost());
        admin.setType(ClubMemberType.CLUBADMIN);
        admin.setValidated(ValidationStatus.nonValidated);
        admin.setValidatedBy(dto.getValidatedBy());
        admin.setCreatedAt(dto.getCreatedAt());
        admin.setUpdatedAt(dto.getUpdatedAt());
        admin.setCreatedBy(dto.getCreatedBy());
        admin.setUpdatedBy(dto.getUpdatedBy());
        admin.setRole(RoleName.CLUB_ADMIN);

        return admin;
    }
}
