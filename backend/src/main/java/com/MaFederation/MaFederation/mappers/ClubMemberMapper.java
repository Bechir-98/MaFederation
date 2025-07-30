package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.services.CategoryService;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClubMemberMapper {

    private final CategoryService categoryService;
    private final ClubRepository clubRepository;
    

    public ResponseClubMemberDTO toResponseDto(ClubMember member) {
        if (member == null) return null;

        ResponseClubMemberDTO dto = new ResponseClubMemberDTO();
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
        dto.setProfilePicture(member.getProfilePicture());
        dto.setType(member.getType());
        dto.setClubId(member.getClub() != null ? member.getClub().getClubId() : null);
        dto.setCategories(member.getCategories()
                      .stream()
                      .map(Category::getName)
                      .collect(Collectors.toList())
            );
        
        return dto;
    }

    public ClubMember toEntity(PostClubMemberDTO dto) {
        if (dto == null) return null;

        ClubMember member;
        switch (dto.getType()) {
            case "PLAYER" -> member = new Player();
            case "STAFF" -> member = new Staff();
            case "ADMIN" -> member = new Administration();
            default -> throw new IllegalArgumentException("Unknown member type: " + dto.getType());
        }

        member.setEmail(dto.getEmail());
        member.setPasswordHash(dto.getPasswordHash());
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setDateOfBirth(dto.getDateOfBirth());
        member.setGender(dto.getGender());
        member.setPhoneNumber(dto.getPhoneNumber());
        member.setAddress(dto.getAddress());
        member.setNationalID(dto.getNationalID());
        member.setNationality(dto.getNationality());
        member.setType(dto.getType());
        member.setClub(clubRepository.findById(dto.getClubId()).orElse(null));
        member.setCategories(categoryService.getCategoriesByIdsEntity(dto.getCategoryIds()));
        member.setProfilePicture(dto.getProfilePicture());

        return member;
    }
}