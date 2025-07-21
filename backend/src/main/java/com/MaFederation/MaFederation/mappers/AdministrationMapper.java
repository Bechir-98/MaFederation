package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.ClubMember.ClubMemberDTO;
import com.MaFederation.MaFederation.dto.Admin.AdministrationDTO;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.model.ClubMember;

@Service
public class AdministrationMapper {

    private final ClubMemberMapper clubMemberMapper;

    public AdministrationMapper(ClubMemberMapper clubMemberMapper) {
        this.clubMemberMapper = clubMemberMapper;
    }

    public AdministrationDTO toDto(Administration admin) {
        if (admin == null) return null;

        AdministrationDTO dto = new AdministrationDTO();

        // Map common ClubMember fields via ClubMemberMapper
        ClubMemberDTO baseDto = clubMemberMapper.toDto(admin);
        dto.setUserId(baseDto.getUserId());
        dto.setEmail(baseDto.getEmail());
        dto.setFirstName(baseDto.getFirstName());
        dto.setLastName(baseDto.getLastName());
        dto.setDateOfBirth(baseDto.getDateOfBirth());
        dto.setGender(baseDto.getGender());
        dto.setPhoneNumber(baseDto.getPhoneNumber());
        dto.setAddress(baseDto.getAddress());
        dto.setNationalID(baseDto.getNationalID());
        dto.setNationality(baseDto.getNationality());
        dto.setRole(baseDto.getRole());
        dto.setClubId(baseDto.getClubId());

        // Include file IDs if available in ClubMemberDTO
        dto.setFileIds(baseDto.getFileIds());

        // Administration-specific fields can be set here if you add any

        return dto;
    }

    public Administration toEntity(AdministrationDTO dto) {
        if (dto == null) return null;

        // Reuse ClubMember mapping for common fields
        ClubMember base = clubMemberMapper.toEntity(dto);

        Administration admin = new Administration();

        // Copy inherited ClubMember fields
        admin.setUserId(base.getUserId());
        admin.setEmail(base.getEmail());
        admin.setFirstName(base.getFirstName());
        admin.setLastName(base.getLastName());
        admin.setDateOfBirth(base.getDateOfBirth());
        admin.setGender(base.getGender());
        admin.setPhoneNumber(base.getPhoneNumber());
        admin.setAddress(base.getAddress());
        admin.setNationalID(base.getNationalID());
        admin.setNationality(base.getNationality());
        admin.setRole(base.getRole());
        admin.setClub(base.getClub());

        // Copy files from base entity if you store files in ClubMember/User entity
        admin.setFiles(base.getFiles());

        // Administration-specific fields can be set here if you add any

        return admin;
    }
}
