package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.Staff.PostStaffDTO;
import com.MaFederation.MaFederation.dto.Staff.ResponceStaffDTO;
import com.MaFederation.MaFederation.mappers.StaffMapper;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.repository.CategoryRepository;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.StaffRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    // Set club
    Club club = clubRepository.findById(dto.getClubId())
        .orElseThrow(() -> new RuntimeException("Club not found with id: " + dto.getClubId()));
    staff.setClub(club);

    // Set categories
    if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        staff.setCategories(categories);
    }

    // Set audit fields (assuming Staff extends Auditable)
    staff.setCreatedAt(LocalDateTime.now());
    staff.setUpdatedAt(LocalDateTime.now());
    // If you track createdBy / updatedBy
    // staff.setCreatedBy(SecurityUtils.getCurrentUserId());

    return staffRepository.save(staff);
}

    public void delete(Integer id) {
    staffRepository.deleteById(id);
}



    public ResponceStaffDTO getStaffById(Integer id) {
        Staff staff= staffRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found"));
        return staffMapper.toDto(staff);
    }



    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }


    public ResponceStaffDTO updateStaff(Integer id, PostStaffDTO dto) {
        Staff staff =  staffRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found"));

        staff.setFirstName(dto.getFirstName());
        staff.setLastName(dto.getLastName());
        staff.setEmail(dto.getEmail());
        staff.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            staff.setCategories(categories);
        }

        Staff update =staffRepository.save(staff);
          
        return staffMapper.toDto(update);
    }


 
}
