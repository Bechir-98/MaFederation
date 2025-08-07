package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.Staff.PostStaffDTO;
import com.MaFederation.MaFederation.mappers.StaffMapper;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.repository.CategoryRepository;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.StaffRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final ClubRepository clubRepository;
    private final CategoryRepository categoryRepository;
    private final StaffMapper staffMapper;

    public Staff createStaff(PostStaffDTO dto) {
        Staff staff = staffMapper.toEntity(dto);

        Club club = clubRepository.findById(dto.getClubId())
            .orElseThrow(() -> new RuntimeException("Club not found"));

        staff.setClub(club);

        if (dto.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            staff.setCategories(categories);
        }

        return staffRepository.save(staff);
    }

    public Staff getStaffById(Integer id) {
        return staffRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff updateStaff(Integer id, PostStaffDTO dto) {
        Staff staff = getStaffById(id);

        staff.setFirstName(dto.getFirstName());
        staff.setLastName(dto.getLastName());
        staff.setEmail(dto.getEmail());
        staff.setPhoneNumber(dto.getPhoneNumber());
        // staff.setFunction(dto.getFunction());

        if (dto.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            staff.setCategories(categories);
        }

        return staffRepository.save(staff);
    }

    public void deleteStaff(Integer id) {
        staffRepository.deleteById(id);
    }
}
