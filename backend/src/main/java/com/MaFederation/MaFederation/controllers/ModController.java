package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
import com.MaFederation.MaFederation.model.Logs;
import com.MaFederation.MaFederation.services.AdministrationService;
import com.MaFederation.MaFederation.services.LogsService;
import com.MaFederation.MaFederation.services.ModService;
import com.MaFederation.MaFederation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/api/v1/management/")
@RequiredArgsConstructor
public class ModController {

    private final ModService modService;
    private final UserService userService;
    private final AdministrationService clubAdminService;
    private final LogsService logsService;


    // Delete moderator
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteModerator(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllAdmins")
    public ResponseEntity<List<ResponseUserDTO>> getAllAdmins() {
        List<ResponseUserDTO> admins = modService.getAllMods(); // call instance method
        return ResponseEntity.ok(admins);
    }

    //club

    @GetMapping("/getAllClubsAdmins")
    public ResponseEntity<List<ResponceAdministrationDTO>> getAllClubsAdmins() {
        return ResponseEntity.ok(clubAdminService.getAllClubsAdmins());
    }
    @GetMapping ("/getLogs")
    public ResponseEntity<List<Logs>> getLogs() {
        return ResponseEntity.ok(this.logsService.getLogs());
    }
}
