package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.Staff.PostStaffDTO;
import com.MaFederation.MaFederation.dto.Staff.ResponceStaffDTO;
import com.MaFederation.MaFederation.mappers.StaffMapper;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.services.StaffService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/staff")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final StaffMapper staffMapper;

    // Create staff with optional profile picture
    @PostMapping("/addstaff")
    public ResponceStaffDTO createStaff(
        @RequestPart("staff") PostStaffDTO dto,
        @RequestPart(value = "profilePicture", required = false) MultipartFile profilePictureFile
    ) throws IOException {

        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
            dto.setProfilePicture(profilePictureFile.getBytes());
        }

        Staff staff = staffService.createStaff(dto);
        return staffMapper.toDto(staff);
    }

    // Select a staff member in session
    @PostMapping
    public ResponseEntity<Void> selectStaff(@RequestBody Map<String, Integer> body, HttpSession session) {
        Integer staffId = body.get("staffId");
        if (staffId == null) {
            return ResponseEntity.badRequest().build();
        }
        session.setAttribute("selectedStaffId", staffId);
        return ResponseEntity.ok().build();
    }

    // Get selected staff member profile from session
    @GetMapping("/profile")
    public ResponseEntity<ResponceStaffDTO> getSelectedStaff(HttpSession session) {
        Integer staffId = (Integer) session.getAttribute("selectedStaffId");
        if (staffId == null) {
            return ResponseEntity.badRequest().build();
        }
        Staff staff = staffService.getStaffById(staffId);
        return ResponseEntity.ok(staffMapper.toDto(staff));
    }
}
