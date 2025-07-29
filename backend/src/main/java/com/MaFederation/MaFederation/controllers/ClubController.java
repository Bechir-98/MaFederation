package com.MaFederation.MaFederation.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.MaFederation.MaFederation.dto.Category.CategoryDTO;
import com.MaFederation.MaFederation.dto.Category.ClubPostCategoryDTO;
import com.MaFederation.MaFederation.dto.Club.PostClubDTO;
import com.MaFederation.MaFederation.dto.Club.ResponseClubDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
import com.MaFederation.MaFederation.enums.FileType;
import com.MaFederation.MaFederation.services.ClubFilesServices;
import com.MaFederation.MaFederation.services.ClubServices;

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

    @GetMapping("/{id}/members")
    public List<ResponseClubMemberDTO> getMembers(@PathVariable Integer id) {
        return clubServices.getMembers(id);
    }

    @GetMapping("/{id}/members/{memberId}")
    public ResponseClubMemberDTO getMemberById(@PathVariable Integer id, @PathVariable  Integer memberId) {
        return clubServices.getClubMember(id, memberId);
    }       

///////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////CLUB FILES RELATED/////////////////////
    @PostMapping("/{clubId}/upload-file")
    public ResponseEntity<?> uploadFile(
        @PathVariable Integer clubId,
        @RequestParam("file") MultipartFile file,
        @RequestParam("type") FileType type) throws IOException {

    clubFileService.saveFile(clubId, file, type);
    return ResponseEntity.ok("File uploaded successfully");
}
////////////////////////////////////////////////////////////////////////////////





////////////////////////////RELATED TO CATEGORIES/////////////////////

@GetMapping("/{clubId}/categories")
public List<CategoryDTO> getCategoriesByClubId(@PathVariable Integer clubId) {
    return clubServices.getCategoriesByClubId(clubId);
}

@PostMapping("/{clubId}/categories")
public CategoryDTO addCategoryToClub(@PathVariable Integer clubId, @RequestBody ClubPostCategoryDTO requestBody) {
    Integer categoryId = requestBody.getCategoryId();
    return clubServices.addCategoryToClub(clubId, categoryId);
}
@DeleteMapping("/{clubId}/categories/{categoryId}")
public ResponseEntity<Void> removeCategoryFromClub(@PathVariable Integer clubId, @PathVariable Integer categoryId) {
    clubServices.removeCategoryFromClub(clubId, categoryId);
    return ResponseEntity.noContent().build();
}

////////////////////////////////////////////////////////////////////////////////
}
