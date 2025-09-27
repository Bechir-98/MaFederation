package com.MaFederation.MaFederation.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.dto.Category.CategoryDTO;
import com.MaFederation.MaFederation.dto.Category.ClubPostCategoryDTO;
import com.MaFederation.MaFederation.dto.Club.ClubFileContent;
import com.MaFederation.MaFederation.dto.Club.ClubFileDTO;
import com.MaFederation.MaFederation.dto.Club.PostClubDTO;
import com.MaFederation.MaFederation.dto.Club.ResponseClubDTO;
import com.MaFederation.MaFederation.dto.Player.ResponsePlayerDTO;
import com.MaFederation.MaFederation.dto.Staff.ResponceStaffDTO;
import com.MaFederation.MaFederation.dto.VerificationRequestResponseDTO.VerificationRequestResponseDTO;
import com.MaFederation.MaFederation.services.ClubFilesServices;
import com.MaFederation.MaFederation.services.ClubServices;
import com.MaFederation.MaFederation.services.UserVerificationRequestService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubServices clubServices;
    private final ClubFilesServices clubFileService;
    private final UserVerificationRequestService verificationservice;
    private final UserService userService;

    /////////////////////////////////// Create a new club ///////////////////////////////////////
    @PostMapping("/register")
    public ResponseEntity<ResponseClubDTO> registerClub(
            @RequestPart("club") PostClubDTO clubDto,
            @RequestPart(value = "logo", required = false) MultipartFile logoFile) throws IOException {

        if (logoFile != null && !logoFile.isEmpty()) {
            clubDto.setLogo(logoFile.getBytes());
        }

        ResponseClubDTO response = clubServices.addClub(clubDto);
        return ResponseEntity.ok(response);
    }

    //////////////////////////// GET ALL CLUBS ////////////////////////
    @Transactional
    @GetMapping
    public List<ResponseClubDTO> getAllClubs() {
        return clubServices.getAllClubs();
    }

    //////////////////////////// GET SELECTED CLUB //////////////////////
    ///
    ///
    @GetMapping("/{clubId}")
    public ResponseEntity<ResponseClubDTO> getClubById(@PathVariable Integer clubId) {
        ResponseClubDTO club = clubServices.getClubById(clubId);
        return ResponseEntity.ok(club);
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseClubDTO> getClub(@AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.status(403).build();
        }
        ResponseClubDTO club = clubServices.getClubById(clubId);
        return ResponseEntity.ok(club);
    }

    ////////////////////// CLUB FILES RELATED /////////////////////
    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type,
            @AuthenticationPrincipal User user) throws IOException {

        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().body("No clubId in JWT");
        }

        clubFileService.saveFile(clubId, file, type);
        Map<String, String> response = new HashMap<>();
        response.put("message", "File uploaded successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/files")
    public ResponseEntity<List<ClubFileDTO>> getFiles(@AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ClubFileDTO> files = clubFileService.getFilesByClubId(clubId);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/files/content")
    public ResponseEntity<ClubFileContent> getFileContent(@RequestParam Integer id,
                                                          @AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        ClubFileContent content = clubFileService.getFileContent(id);
        return ResponseEntity.ok(content);
    }

    @PutMapping("/files")
    public ResponseEntity<ClubFileDTO> updateFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileId") Integer fileId,
            @AuthenticationPrincipal User user) {

        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        ClubFileDTO updated = clubFileService.updateFile(fileId, file);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/files")
    public ResponseEntity<String> deleteFile(@RequestParam("fileId") Integer fileId,
                                             @AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().body("No clubId in JWT");
        }
        clubFileService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted successfully.");
    }

    //////////////////////////// RELATED TO CATEGORIES //////////////////////
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories(@AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<CategoryDTO> categories = clubServices.getCategoriesByClubId(clubId);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> addCategoryToClub(@RequestBody ClubPostCategoryDTO requestBody,
                                                         @AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        Integer categoryId = requestBody.getId();
        CategoryDTO category = clubServices.addCategoryToClub(clubId, categoryId);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> removeCategoryFromClub(@PathVariable Integer categoryId,
                                                       @AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        clubServices.removeCategoryFromClub(clubId, categoryId);
        return ResponseEntity.noContent().build();
    }

    //////////////////////////////// STAFF, ADMINISTRATION, PLAYERS //////////////////////////////
    @GetMapping("/staff")
    public ResponseEntity<List<ResponceStaffDTO>> getClubStaff(@AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ResponceStaffDTO> staff = clubServices.getStaffByClubId(clubId);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/administration")
    public ResponseEntity<List<ResponceAdministrationDTO>> getClubAdministration(@AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ResponceAdministrationDTO> admins = clubServices.getAdministrationByClubId(clubId);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/players")
    public ResponseEntity<List<ResponsePlayerDTO>> getClubPlayers(@AuthenticationPrincipal User user) {
        Integer clubId = extractClubId(user);
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ResponsePlayerDTO> players = clubServices.getPlayersByClubId(clubId);
        return ResponseEntity.ok(players);
    }

    @PostMapping("/request-validation")
    public ResponseEntity<VerificationRequestResponseDTO> requestValidation(
            @RequestBody VerificationRequestResponseDTO dto) {
        VerificationRequestResponseDTO response =
                verificationservice.createRequestForUser(dto.getUserId(), dto.getClubId());
        return ResponseEntity.ok(response);
    }

    /////////////////// Delete Member
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /////////////////// Helper: Extract clubId from JWT or principal
    private Integer extractClubId(User user) {
        // Case 1: Club Admin → linked directly
        if (user instanceof Administration adminUser && adminUser.getClub() != null) {
            return adminUser.getClub().getId();
        }

        // Case 2: Super Admin → clubId in JWT claim
        Object auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            Object claim = jwtAuth.getToken().getClaims().get("clubId");
            if (claim != null) {
                return Integer.valueOf(claim.toString());
            }
        }
        return null;
    }
}
