package com.MaFederation.MaFederation.mappers;
import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;
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

    // Convert Administration entity to AdministrationDTO
    public ResponceAdministrationDTO toDto(Administration administration) {
        if (administration == null) return null;

        ResponceClubMemberDTO baseDto = clubMemberMapper.toResponseDto(administration);

        ResponceAdministrationDTO dto = new ResponceAdministrationDTO();
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
        dto.setClubId(baseDto.getClubId());

        // Administration-specific fields (if any) can go here

        return dto;
    }

    // Convert AdministrationDTO to Administration entity
    public Administration toEntity(PostAdminstrationDTO dto) {
        if (dto == null) return null;

        Administration admin = new Administration();

        // Common ClubMember fields
        admin.setUserId(dto.getUserId());
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
        admin.setPasswordHash(dto.getPasswordHash()); // ✅ Include password hash!

        if (dto.getClubId() != null) {
            admin.setClub(clubServices.getClub(dto.getClubId()));
        }

        return admin;
    }
}
