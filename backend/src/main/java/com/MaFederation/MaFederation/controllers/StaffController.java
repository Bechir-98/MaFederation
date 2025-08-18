package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.Staff.PostStaffDTO;
import com.MaFederation.MaFederation.dto.Staff.ResponceStaffDTO;
import com.MaFederation.MaFederation.mappers.StaffMapper;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.services.StaffService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    @PutMapping("/{id}")
public ResponseEntity<ResponceStaffDTO> updateStaff(
        @PathVariable Integer id,
        @RequestBody PostStaffDTO dto) {

    try {
        ResponceStaffDTO updatedStaff = staffService.updateStaff(id, dto);
        return ResponseEntity.ok(updatedStaff); // Angular receives full JSON
    } catch (EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}





}
