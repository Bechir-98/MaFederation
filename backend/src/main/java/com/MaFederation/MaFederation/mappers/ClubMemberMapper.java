package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.ClubMember.ClubMemberDTO;
import com.MaFederation.MaFederation.dto.User.UserDTO;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.model.Staff;
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

    // Convert ClubMember entity (or subclass) to DTO
    public ClubMemberDTO toDto(ClubMember member) {
        if (member == null) return null;

        ClubMemberDTO dto = new ClubMemberDTO();

        // Map common User fields via UserMapper
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
        dto.setFileIds(userDto.getFileIds());  // if you handle files

        // ClubMember specific
        dto.setRole(member.getRole());
        dto.setClubId(member.getClub() != null ? member.getClub().getClubID() : null);

        // Important: set the type field for polymorphic identification
        dto.setType(member.getType());

        return dto;
    }

    // Convert DTO back to entity (concrete subclass based on type)
    public ClubMember toEntity(ClubMemberDTO dto) {
        if (dto == null) return null;

        ClubMember member;

        // Instantiate correct subclass based on dto.type
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
        User baseUser = userMapper.fromDto(dto);
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
        member.setFiles(baseUser.getFiles()); // if applicable

        // ClubMember-specific
        member.setRole(dto.getRole());
        member.setType(dto.getType());

        // Load club entity via service if clubId is present
        if (dto.getClubId() != null) {
            member.setClub(clubServices.getClub(dto.getClubId()));
        }

        return member;
    }
}
