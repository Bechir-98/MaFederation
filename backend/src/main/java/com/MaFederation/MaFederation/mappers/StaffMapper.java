package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;
import com.MaFederation.MaFederation.dto.StaffDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Staff;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffMapper {

    public StaffDTO toDto(Staff staff) {
        if (staff == null) return null;

        List<Integer> categoryIds = null;
        if (staff.getCategories() != null) {
            categoryIds = staff.getCategories()
                .stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toList());
        }

        return new StaffDTO(
            staff.getUserId(),
            staff.getSpecialty(),
            categoryIds
        );
    }

    public Staff toEntity(StaffDTO dto, List<Category> categories) {
        if (dto == null) return null;

        Staff staff = new Staff();
        staff.setUserId(dto.userId());
        staff.setSpecialty(dto.specialty());
        staff.setCategories(categories);

        return staff;
    }
}
