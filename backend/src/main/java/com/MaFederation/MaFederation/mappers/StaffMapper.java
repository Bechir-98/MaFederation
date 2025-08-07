package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.Staff.PostStaffDTO;
import com.MaFederation.MaFederation.dto.Staff.ResponceStaffDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Staff;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StaffMapper {

    public ResponceStaffDTO toDto(Staff staff) {
        if (staff == null) return null;

        ResponceStaffDTO dto = new ResponceStaffDTO();

        dto.setId(staff.getId());
        dto.setEmail(staff.getEmail());
        dto.setFirstName(staff.getFirstName());
        dto.setLastName(staff.getLastName());
        dto.setDateOfBirth(staff.getDateOfBirth());
        dto.setGender(staff.getGender());
        dto.setPhoneNumber(staff.getPhoneNumber());
        dto.setAddress(staff.getAddress());
        dto.setNationalID(staff.getNationalID());
        dto.setNationality(staff.getNationality());

        if (staff.getClub() != null) {
            dto.setClubId(staff.getClub().getId());
        }

        dto.setProfilePicture(staff.getProfilePicture());
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

    public Staff toEntity(PostStaffDTO dto) {
        if (dto == null) return null;

        Staff staff = new Staff();

        staff.setEmail(dto.getEmail());
        staff.setPasswordHash(dto.getPasswordHash());
        staff.setProfilePicture(dto.getProfilePicture());
        staff.setFirstName(dto.getFirstName());
        staff.setLastName(dto.getLastName());
        staff.setDateOfBirth(dto.getDateOfBirth());
        staff.setGender(dto.getGender());
        staff.setPhoneNumber(dto.getPhoneNumber());
        staff.setAddress(dto.getAddress());
        staff.setNationalID(dto.getNationalID());
        staff.setNationality(dto.getNationality());

        staff.setSpecialty(dto.getSpecialty());

        // No club or categories set here!

        return staff;
    }
}
