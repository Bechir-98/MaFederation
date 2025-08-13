package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.services.AdministrationService;
import com.MaFederation.MaFederation.mappers.AdministrationMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/administration")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class AdministrationController {

    private final AdministrationService administrationService;
    private final AdministrationMapper administrationMapper;

    // ✅ Add administration (with optional profile picture)
    @PostMapping("/add")
    public ResponceAdministrationDTO createAdministration(
            @RequestPart("administration") PostAdminstrationDTO dto,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePictureFile
    ) throws IOException {
        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
            dto.setProfilePicture(profilePictureFile.getBytes());
        }
        Administration admin = administrationService.createAdministration(dto);
        return administrationMapper.toDto(admin);
    }

    // ✅ Select administration into session
    @PostMapping
    public ResponseEntity<Void> selectAdministration(@RequestBody Map<String, Integer> body, HttpSession session) {
        Integer adminId = body.get("adminId");
        if (adminId == null) {
            return ResponseEntity.badRequest().build();
        }
        session.setAttribute("selectedAdminId", adminId);
        return ResponseEntity.ok().build();
    }

    // ✅ Get selected administration profile from session
    @GetMapping("/profile")
    public ResponseEntity<ResponceAdministrationDTO> getSelectedAdministration(HttpSession session) {
        Integer adminId = (Integer) session.getAttribute("selectedAdminId");
        if (adminId == null) {
            return ResponseEntity.badRequest().build();
        }
        Administration admin = administrationService.getById(adminId);
        return ResponseEntity.ok(administrationMapper.toDto(admin));
    }

}
