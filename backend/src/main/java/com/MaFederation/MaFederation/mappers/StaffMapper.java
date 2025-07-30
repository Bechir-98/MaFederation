package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
import com.MaFederation.MaFederation.dto.Staff.PostStaffDTO;
import com.MaFederation.MaFederation.dto.Staff.ResponceStaffDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.services.CategoryService;
import com.MaFederation.MaFederation.services.ClubServices;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class StaffMapper {

    private final ClubMemberMapper clubMemberMapper;
    private final ClubServices clubServices;
    private final CategoryService categoryService;

    public StaffMapper(ClubMemberMapper clubMemberMapper, ClubServices clubServices, CategoryService categoryService) {
        this.clubMemberMapper = clubMemberMapper;
        this.clubServices = clubServices;
        this.categoryService = categoryService;
    }

    // Convert Staff entity to ResponceStaffDTO
    public ResponceStaffDTO toDto(Staff staff) {
        if (staff == null) return null;

        ResponseClubMemberDTO baseDto = clubMemberMapper.toResponseDto(staff);

        ResponceStaffDTO dto = new ResponceStaffDTO();
        dto.setUserId(baseDto.getUserId());
        dto.setProfilePicture(baseDto.getProfilePicture());
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
        dto.setProfilePicture(baseDto.getProfilePicture());
        

        dto.setSpecialty(staff.getSpecialty());

        if (staff.getCategories() != null) {
            dto.setCategories(
                staff.getCategories()
                     .stream()
                     .map(Category::getName)
                     .collect(Collectors.toList())
            );
        }

        return dto;
    }

    // Convert PostStaffDTO to Staff entity
    public Staff toEntity(PostStaffDTO dto) {
        if (dto == null) return null;

        Staff staff = new Staff();

        staff.setProfilePicture(dto.getProfilePicture());
        staff.setPasswordHash(dto.getPasswordHash());
        staff.setEmail(dto.getEmail());
        staff.setFirstName(dto.getFirstName());
        staff.setLastName(dto.getLastName());
        staff.setDateOfBirth(dto.getDateOfBirth());
        staff.setGender(dto.getGender());
        staff.setPhoneNumber(dto.getPhoneNumber());
        staff.setAddress(dto.getAddress());
        staff.setNationalID(dto.getNationalID());
        staff.setNationality(dto.getNationality());
        staff.setType("STAFF");
        staff.setProfilePicture(dto.getProfilePicture());

        if (dto.getClubId() != null) {
            staff.setClub(clubServices.getClub(dto.getClubId()));
        }

        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            staff.setCategories(categoryService.getCategoriesByIdsEntity(dto.getCategoryIds()));
        }

        staff.setSpecialty(dto.getSpecialty());

        return staff;
    }
}
