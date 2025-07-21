package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.ClubMemberDTO;
import com.MaFederation.MaFederation.dto.StaffDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.model.Staff;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffMapper {

    private final ClubMemberMapper clubMemberMapper;

    public StaffMapper(ClubMemberMapper clubMemberMapper) {
        this.clubMemberMapper = clubMemberMapper;
    }

    public StaffDTO toDto(Staff staff) {
        if (staff == null) {
            return null;
        }

        StaffDTO dto = new StaffDTO();
        ClubMember base = staff;

        // Map common ClubMember fields
        ClubMemberDTO baseDto = clubMemberMapper.toDto(base);
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

        // Add file IDs from baseDto (ClubMemberDTO)
        dto.setFileIds(baseDto.getFileIds());

        // Staff-specific fields
        dto.setSpecialty(staff.getSpecialty());

        // Handle categories
        if (staff.getCategories() != null) {
            List<Integer> categoryIds = staff.getCategories()
                .stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toList());
            dto.setCategoryIds(categoryIds);
        }

        return dto;
    }

    public Staff toEntity(StaffDTO dto, List<Category> categories) {
        if (dto == null) {
            return null;
        }

        // Reuse ClubMember mapping for common fields
        ClubMember base = clubMemberMapper.toEntity(dto);

        Staff staff = new Staff();

        // Copy inherited ClubMember fields
        staff.setUserId(base.getUserId());
        staff.setEmail(base.getEmail());
        staff.setFirstName(base.getFirstName());
        staff.setLastName(base.getLastName());
        staff.setDateOfBirth(base.getDateOfBirth());
        staff.setGender(base.getGender());
        staff.setPhoneNumber(base.getPhoneNumber());
        staff.setAddress(base.getAddress());
        staff.setNationalID(base.getNationalID());
        staff.setNationality(base.getNationality());
        staff.setRole(base.getRole());
        staff.setClub(base.getClub());

        // Copy files from base entity
        staff.setFiles(base.getFiles());

        // Set Staff-specific fields
        staff.setSpecialty(dto.getSpecialty());
        staff.setCategories(categories);

        return staff;
    }
}
