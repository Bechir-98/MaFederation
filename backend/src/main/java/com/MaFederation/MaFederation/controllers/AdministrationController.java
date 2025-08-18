package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.services.AdministrationService;
import com.MaFederation.MaFederation.mappers.AdministrationMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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


    @PutMapping("/{id}")
public ResponseEntity<ResponceAdministrationDTO> updateAdministration(
        @PathVariable Integer id,
        @RequestBody PostAdminstrationDTO dto) {

    try {
        ResponceAdministrationDTO updatedAdmin = administrationService.update(id, dto);
        return ResponseEntity.ok(updatedAdmin); // Angular receives full JSON
    } catch (EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}



}
