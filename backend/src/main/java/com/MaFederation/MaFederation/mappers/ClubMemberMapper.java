package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;
import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.services.CategoryService;
import com.MaFederation.MaFederation.services.ClubServices;

@Service
public class ClubMemberMapper {

    private final CategoryService categoryService;
    private final ClubServices clubServices;

    public ClubMemberMapper(ClubServices clubServices, CategoryService categoryService) {
        this.clubServices = clubServices;

        this.categoryService = categoryService;
    }

    // Convert ClubMember entity (or subclass) to DTO
    public ResponceClubMemberDTO toResponseDto(ClubMember member) {
        if (member == null) return null;

        ResponceClubMemberDTO dto = new ResponceClubMemberDTO();
        dto.setUserId(member.getUserId());
        dto.setEmail(member.getEmail());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setDateOfBirth(member.getDateOfBirth());
        dto.setGender(member.getGender());
        dto.setPhoneNumber(member.getPhoneNumber());
        dto.setAddress(member.getAddress());
        dto.setNationalID(member.getNationalID());
        dto.setNationality(member.getNationality());
        dto.setClubId(member.getClub() != null ? member.getClub().getClubID() : null);
        dto.setType(member.getType());


        return dto;
    }

    // Convert DTO back to entity (concrete subclass based on type)
    public ClubMember toEntity(PostClubMemberDTO dto) {
        if (dto == null) return null;

        ClubMember member;
        switch (dto.getType()) {
            case "PLAYER":
                member = new Player();
                break;
            case "STAFF":
                member = new Staff();
                break;
            case "ADMIN":
                member = new Administration();
                break;
            default:
                throw new IllegalArgumentException("Unknown member type: " + dto.getType());
        }

        // Map User fields
      
        member.setUserId(dto.getUserId());
        member.setEmail(dto.getEmail());
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setDateOfBirth(dto.getDateOfBirth());
        member.setGender(dto.getGender());
        member.setPhoneNumber(dto.getPhoneNumber());
        member.setAddress(dto.getAddress());
        member.setNationalID(dto.getNationalID());
        member.setNationality(dto.getNationality());
        member.setType(dto.getType());
        member.setPasswordHash(dto.getPasswordHash());
        member.setCategories(categoryService.getCategoriesByIdsEntity( dto.getCategoryIds()));

        // Load club entity via service if clubId is present
        if (dto.getClubId() != null) {
            member.setClub(clubServices.getClub(dto.getClubId()));
        }

        return member;
    }




}
