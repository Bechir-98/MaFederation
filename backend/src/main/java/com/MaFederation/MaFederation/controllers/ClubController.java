package com.MaFederation.MaFederation.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
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
import com.MaFederation.MaFederation.model.VerificationRequest;
import com.MaFederation.MaFederation.services.ClubFilesServices;
import com.MaFederation.MaFederation.services.ClubServices;
import com.MaFederation.MaFederation.services.VerificationRequestService;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubServices clubServices;
    private final ClubFilesServices clubFileService;
    private final VerificationRequestService verificationservice;

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

    ///////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////// GET ALL CLUBS ////////////////////////
    @Transactional
    @GetMapping
    public List<ResponseClubDTO> getAllClubs() {
        return clubServices.getAllClubs();
    }

    //////////////////////////// SESSION: SELECT CLUB //////////////////////
    @PostMapping("/select")
    public ResponseEntity<Void> selectClub(@RequestBody Map<String, Integer> body, HttpSession session) {
        Integer clubId = body.get("clubId");
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        session.setAttribute("selectedClubId", clubId);
        return ResponseEntity.ok().build();
    }

    //////////////////////////// SESSION: GET SELECTED CLUB //////////////////////
    @GetMapping("/profile")
    public ResponseEntity<ResponseClubDTO> getSelectedClub(HttpSession session) {
        Integer clubId = (Integer) session.getAttribute("selectedClubId");
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        ResponseClubDTO club = clubServices.getClubById(clubId);
        return ResponseEntity.ok(club);
    }

    ////////////////////// CLUB FILES RELATED /////////////////////

@PostMapping("/upload-file")
public ResponseEntity<?> uploadFile(
    @RequestParam("file") MultipartFile file,
    @RequestParam("type") String type,
    HttpSession session) throws IOException {

    Integer clubId = (Integer) session.getAttribute("selectedClubId");
    if (clubId == null) {
        return ResponseEntity.badRequest().body("No club selected in session");
    }

    Map<String, String> response = new HashMap<>();
    clubFileService.saveFile(clubId, file, type);
    response.put("message", "File uploaded successfully");
    return ResponseEntity.ok(response);
}

@GetMapping("/files")
public ResponseEntity<List<ClubFileDTO>> getFiles(HttpSession session) {
    Integer clubId = (Integer) session.getAttribute("selectedClubId");
    if (clubId == null) {
        return ResponseEntity.badRequest().build();
    }
    List<ClubFileDTO> files = clubFileService.getFilesByClubId(clubId);
    return ResponseEntity.ok(files);
}

@GetMapping("/files/content")
public ResponseEntity<ClubFileContent> getFileContent(@RequestParam Integer id, HttpSession session) {
    Integer clubId = (Integer) session.getAttribute("selectedClubId");
    if (clubId == null) {
        return ResponseEntity.badRequest().build();
    }
    // Optionally validate file belongs to clubId here
    ClubFileContent content = clubFileService.getFileContent(id);
    return ResponseEntity.ok(content);
}

@PutMapping("/files")
public ResponseEntity<ClubFileDTO> updateFile(
    @RequestParam("file") MultipartFile file,
    @RequestParam("fileId") Integer fileId,
    HttpSession session
) {
    Integer clubId = (Integer) session.getAttribute("selectedClubId");
    if (clubId == null) {
        return ResponseEntity.badRequest().build();
    }
    // Optionally verify fileId ownership with clubId

    ClubFileDTO updated = clubFileService.updateFile(fileId, file);
    return ResponseEntity.ok(updated);
}

@DeleteMapping("/files")
public ResponseEntity<String> deleteFile(@RequestParam("fileId") Integer fileId, HttpSession session) {
    Integer clubId = (Integer) session.getAttribute("selectedClubId");
    if (clubId == null) {
        return ResponseEntity.badRequest().body("No club selected in session");
    }
    // Optionally verify fileId ownership with clubId

    clubFileService.deleteFile(fileId);
    return ResponseEntity.ok("File deleted successfully.");
}


    //////////////////////////// RELATED TO CATEGORIES //////////////////////

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories(HttpSession session) {
        Integer clubId = (Integer) session.getAttribute("selectedClubId");
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<CategoryDTO> categories = clubServices.getCategoriesByClubId(clubId);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> addCategoryToClub(@RequestBody ClubPostCategoryDTO requestBody, HttpSession session) {
        Integer clubId = (Integer) session.getAttribute("selectedClubId");
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        Integer categoryId = requestBody.getId();
        CategoryDTO category = clubServices.addCategoryToClub(clubId, categoryId);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> removeCategoryFromClub(@PathVariable Integer categoryId, HttpSession session) {
        Integer clubId = (Integer) session.getAttribute("selectedClubId");
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        clubServices.removeCategoryFromClub(clubId, categoryId);
        return ResponseEntity.noContent().build();
    }

    //////////////////////////////// STAFF, ADMINISTRATION, PLAYERS //////////////////////////////

    @GetMapping("/staff")
    public ResponseEntity<List<ResponceStaffDTO>> getClubStaff(HttpSession session) {
        Integer clubId = (Integer) session.getAttribute("selectedClubId");
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ResponceStaffDTO> staff = clubServices.getStaffByClubId(clubId);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/administration")
    public ResponseEntity<List<ResponceAdministrationDTO>> getClubAdministration(HttpSession session) {
        Integer clubId = (Integer) session.getAttribute("selectedClubId");
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ResponceAdministrationDTO> admins = clubServices.getAdministrationByClubId(clubId);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/players")
    public ResponseEntity<List<ResponsePlayerDTO>> getClubPlayers(HttpSession session) {
        Integer clubId = (Integer) session.getAttribute("selectedClubId");
        if (clubId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ResponsePlayerDTO> players = clubServices.getPlayersByClubId(clubId);
        return ResponseEntity.ok(players);
    }



@PostMapping("/request-validation")
public ResponseEntity<VerificationRequestResponseDTO> requestValidation(
        @RequestBody VerificationRequestResponseDTO dto) {

    VerificationRequestResponseDTO response = verificationservice.createRequestForUser(dto.getUserId(), dto.getClubId());

    return ResponseEntity.ok(response);
}



// @Data
// public class VerificationRequestDTO {
//     private Integer userId;
//     private Integer clubId;
// }
}
