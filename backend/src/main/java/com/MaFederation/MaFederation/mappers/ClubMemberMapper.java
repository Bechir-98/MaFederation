package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.ClubMemberDTO;
import com.MaFederation.MaFederation.dto.UserDTO;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.services.ClubServices;

@Service
public class ClubMemberMapper {

    private final ClubServices clubServices;
    private final UserMapper userMapper;

    public ClubMemberMapper(ClubServices clubServices, UserMapper userMapper) {
        this.clubServices = clubServices;
        this.userMapper = userMapper;
    }

    public ClubMemberDTO toDto(ClubMember member) {
        if (member == null) return null;

        ClubMemberDTO dto = new ClubMemberDTO();

        // Reuse UserMapper for inherited fields (including fileIds)
        UserDTO userDto = userMapper.toDto(member);
        dto.setUserId(userDto.getUserId());
        dto.setEmail(userDto.getEmail());
        dto.setFirstName(userDto.getFirstName());
        dto.setLastName(userDto.getLastName());
        dto.setDateOfBirth(userDto.getDateOfBirth());
        dto.setGender(userDto.getGender());
        dto.setPhoneNumber(userDto.getPhoneNumber());
        dto.setAddress(userDto.getAddress());
        dto.setNationalID(userDto.getNationalID());
        dto.setNationality(userDto.getNationality());
        dto.setFileIds(userDto.getFileIds());  // <- files here

        // ClubMember specific fields
        dto.setRole(member.getRole());
        dto.setClubId(member.getClub() != null ? member.getClub().getClubID() : null);

        return dto;
    }

    public ClubMember toEntity(ClubMemberDTO dto) {
        if (dto == null) return null;

        // Use userMapper to set base fields
        ClubMember member = new ClubMember();
        User baseUser = userMapper.fromDto(dto);
        // Copy all user fields to member
        member.setUserId(baseUser.getUserId());
        member.setEmail(baseUser.getEmail());
        member.setFirstName(baseUser.getFirstName());
        member.setLastName(baseUser.getLastName());
        member.setDateOfBirth(baseUser.getDateOfBirth());
        member.setGender(baseUser.getGender());
        member.setPhoneNumber(baseUser.getPhoneNumber());
        member.setAddress(baseUser.getAddress());
        member.setNationalID(baseUser.getNationalID());
        member.setNationality(baseUser.getNationality());
        member.setFiles(baseUser.getFiles()); // if you load files in service

        // ClubMember-specific
        member.setRole(dto.getRole());

        // Club loading should be done in service layer, but if you want here:
        member.setClub(clubServices.getClub(dto.getClubId()));

        return member;
    }
}
