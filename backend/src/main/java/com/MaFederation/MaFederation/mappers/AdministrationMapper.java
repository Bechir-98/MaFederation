package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.services.ClubServices;
import org.springframework.stereotype.Service;

@Service
public class AdministrationMapper {

    private final ClubMemberMapper clubMemberMapper;
    private final ClubServices clubServices;

    public AdministrationMapper(ClubMemberMapper clubMemberMapper, ClubServices clubServices) {
        this.clubMemberMapper = clubMemberMapper;
        this.clubServices = clubServices;
    }

    // Convert Administration entity to ResponceAdministrationDTO
    public ResponceAdministrationDTO toDto(Administration administration) {
        if (administration == null) return null;

        ResponseClubMemberDTO baseDto = clubMemberMapper.toResponseDto(administration);

        ResponceAdministrationDTO dto = new ResponceAdministrationDTO();
        dto.setUserId(baseDto.getUserId());
        dto.setEmail(baseDto.getEmail());
        dto.setProfilePicture(baseDto.getProfilePicture());
        dto.setFirstName(baseDto.getFirstName());
        dto.setLastName(baseDto.getLastName());
        dto.setDateOfBirth(baseDto.getDateOfBirth());
        dto.setGender(baseDto.getGender());
        dto.setPhoneNumber(baseDto.getPhoneNumber());
        dto.setAddress(baseDto.getAddress());
        dto.setNationalID(baseDto.getNationalID());
        dto.setNationality(baseDto.getNationality());
        dto.setClubId(baseDto.getClubId());

        // Add Administration-specific fields here if any

        return dto;
    }

    // Convert PostAdminstrationDTO to Administration entity
    public Administration toEntity(PostAdminstrationDTO dto) {
        if (dto == null) return null;

        Administration admin = new Administration();

        admin.setPasswordHash(dto.getPasswordHash());
        admin.setProfilePicture(dto.getProfilePicture());
        admin.setEmail(dto.getEmail());
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setDateOfBirth(dto.getDateOfBirth());
        admin.setGender(dto.getGender());
        admin.setPhoneNumber(dto.getPhoneNumber());
        admin.setAddress(dto.getAddress());
        admin.setNationalID(dto.getNationalID());
        admin.setNationality(dto.getNationality());
        admin.setType("ADMINISTRATION");

        if (dto.getClubId() != null) {
            admin.setClub(clubServices.getClub(dto.getClubId()));
        }

        return admin;
    }
}
