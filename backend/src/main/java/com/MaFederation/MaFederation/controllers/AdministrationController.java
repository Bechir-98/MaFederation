package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.services.AdministrationService;
import com.MaFederation.MaFederation.mappers.AdministrationMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/administration")
@RequiredArgsConstructor
public class AdministrationController {

    private final AdministrationService administrationService;
    private final AdministrationMapper administrationMapper;

    @PostMapping
    public ResponseEntity<ResponceAdministrationDTO> create(@RequestBody PostAdminstrationDTO dto) {
        Administration admin = administrationService.createAdministration(dto);
        return ResponseEntity.ok(administrationMapper.toDto(admin));
    }

    @GetMapping
    public ResponseEntity<List<ResponceAdministrationDTO>> getAll() {
        List<Administration> admins = administrationService.getAll();
        List<ResponceAdministrationDTO> dtos = admins.stream()
                .map(administrationMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponceAdministrationDTO> getById(@PathVariable Integer id) {
        Administration admin = administrationService.getById(id);
        return ResponseEntity.ok(administrationMapper.toDto(admin));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponceAdministrationDTO> update(@PathVariable Integer id, @RequestBody PostAdminstrationDTO dto) {
        Administration updated = administrationService.update(id, dto);
        return ResponseEntity.ok(administrationMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        administrationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
