package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.Staff.PostStaffDTO;
import com.MaFederation.MaFederation.dto.Staff.ResponceStaffDTO;
import com.MaFederation.MaFederation.mappers.StaffMapper;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.services.StaffService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final StaffMapper staffMapper;

    @PostMapping
    public ResponseEntity<ResponceStaffDTO> createStaff(@RequestBody PostStaffDTO dto) {
        Staff staff = staffService.createStaff(dto);
        return ResponseEntity.ok(staffMapper.toDto(staff));
    }

    @GetMapping
    public ResponseEntity<List<ResponceStaffDTO>> getAllStaff() {
        List<Staff> staffList = staffService.getAllStaff();
        List<ResponceStaffDTO> dtos = staffList.stream()
                                               .map(staffMapper::toDto)
                                               .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponceStaffDTO> getStaffById(@PathVariable Integer id) {
        Staff staff = staffService.getStaffById(id);
        return ResponseEntity.ok(staffMapper.toDto(staff));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponceStaffDTO> updateStaff(@PathVariable Integer id, @RequestBody PostStaffDTO dto) {
        Staff updated = staffService.updateStaff(id, dto);
        return ResponseEntity.ok(staffMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Integer id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
