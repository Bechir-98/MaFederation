package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.mod.ModDTO;
import com.MaFederation.MaFederation.model.Role;
import com.MaFederation.MaFederation.services.ModService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/mods")
@RequiredArgsConstructor
public class ModController {

    private final ModService modService;

    // Create new moderator
    @PostMapping
    public ResponseEntity<ModDTO> createModerator(@RequestBody ModDTO modDTO) {
        try {
            ModDTO created = modService.createModerator(modDTO);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Select moderator in session
    @PostMapping("/select")
    public ResponseEntity<Void> selectModerator(@RequestBody Map<String, Integer> body, HttpSession session) {
        Integer moderatorId = body.get("moderatorId");
        if (moderatorId == null) {
            return ResponseEntity.badRequest().build();
        }
        modService.selectModeratorInSession(session, moderatorId, null);
        return ResponseEntity.ok().build();
    }

    // Get selected moderator
    @GetMapping("/selected")
    public ResponseEntity<ModDTO> getSelectedModerator(HttpSession session) {
        return modService.getSelectedModerator(session)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    // Get all moderators
    @GetMapping
    public ResponseEntity<List<ModDTO>> getAllModerators() {
        return ResponseEntity.ok(modService.getAllModerators());
    }

    // Update roles for a moderator
    @PutMapping("/{id}/roles")
    public ResponseEntity<ModDTO> updateModeratorRoles(@PathVariable Integer id, @RequestBody Set<Role> roles) {
        try {
            ModDTO updated = modService.updateModeratorRoles(id, roles);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete moderator
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModerator(@PathVariable Integer id) {
        try {
            modService.deleteModerator(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
