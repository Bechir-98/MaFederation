package com.MaFederation.MaFederation.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.MaFederation.MaFederation.services.ClubFilesServices;
import com.MaFederation.MaFederation.services.ClubServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;




@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {
    
    private final ClubServices clubServices;
    private final ClubFilesServices clubFileService;

   /////////////////////////////////// Create a new club///////////////////////////////////////
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

///////////////////////////////////////////////////////////////////////////

    ////////////////////////////GETING CLUB////////////////////////
    // Get all clubs as Response DTOs
    @Transactional
    @GetMapping
    public List<ResponseClubDTO> getAllClubs() {
        return clubServices.getAllClubs();
    }

    // Get one club by ID as Response DTO
    @GetMapping("/{id}")
    public ResponseClubDTO getClubById(@PathVariable int id) {
        return clubServices.getClubById(id);
    }
///////////////////////////////////////////////////////////////////////////
   

        ////////////////////////MEMBERS RELATED/////////////////////

// Get all members of a specific club


///////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////CLUB FILES RELATED/////////////////////
    @PostMapping("/{clubId}/upload-file")
    public ResponseEntity<?> uploadFile(
        @PathVariable Integer clubId,
        @RequestParam("file") MultipartFile file,
        @RequestParam("type") String type) throws IOException {
    Map<String, String> response = new HashMap<>();
    clubFileService.saveFile(clubId, file, type);
    response.put("message", "File uploaded successfully");
    return ResponseEntity.ok(response);
}

@GetMapping("/{clubId}/files")
public List<ClubFileDTO> getFilesByClubId(@PathVariable Integer clubId) {
    return clubFileService.getFilesByClubId(clubId);
}

@GetMapping("/{clubId}/files/content")
public ClubFileContent getFileContent( @RequestParam Integer id) {
    return clubFileService.getFileContent(id);
}

@PutMapping("/{clubId}/files")
public ResponseEntity<ClubFileDTO> updateFile(
    @PathVariable Long clubId,
    @RequestParam("file") MultipartFile file,
    @RequestParam("fileId") Integer fileId
) {
    ClubFileDTO updated = clubFileService.updateFile(fileId, file);
    return ResponseEntity.ok(updated);
}

@DeleteMapping("/{clubId}/files")
public ResponseEntity<String> deleteFile(@RequestParam("fileId") Integer fileId) {
    clubFileService.deleteFile(fileId);
    return ResponseEntity.ok("File deleted successfully.");
}




////////////////////////////////////////////////////////////////////////////////


////////////////////////////RELATED TO CATEGORIES/////////////////////

@GetMapping("/{clubId}/categories")
public List<CategoryDTO> getCategoriesByClubId(@PathVariable Integer clubId) {
    return clubServices.getCategoriesByClubId(clubId);
}

@PostMapping("/{clubId}/categories")
public CategoryDTO addCategoryToClub(@PathVariable Integer clubId, @RequestBody ClubPostCategoryDTO requestBody) {
    Integer categoryId = requestBody.getId();
    return clubServices.addCategoryToClub(clubId, categoryId);
}
@DeleteMapping("/{clubId}/categories/{categoryId}")
public ResponseEntity<Void> removeCategoryFromClub(@PathVariable Integer clubId, @PathVariable Integer categoryId) {
    clubServices.removeCategoryFromClub(clubId, categoryId);
    return ResponseEntity.noContent().build();
}

////////////////////////////////////////////////////////////////////////////////


@GetMapping("/staff")
    public ResponseEntity<List<ResponceStaffDTO>> getClubStaff(@RequestParam Integer clubId) {
        List<ResponceStaffDTO> staff = clubServices.getStaffByClubId(clubId);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/administration")
    public ResponseEntity<List<ResponceAdministrationDTO>> getClubAdministration(@RequestParam Integer clubId) {
        List<ResponceAdministrationDTO> admins = clubServices.getAdministrationByClubId(clubId);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/players")
    public ResponseEntity<List<ResponsePlayerDTO>> getClubPlayers(@RequestParam Integer clubId) {
        List<ResponsePlayerDTO> players = clubServices.getPlayersByClubId(clubId);
        return ResponseEntity.ok(players);
    }
}


