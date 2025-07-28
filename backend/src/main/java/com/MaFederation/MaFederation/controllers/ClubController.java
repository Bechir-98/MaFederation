package com.MaFederation.MaFederation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.MaFederation.MaFederation.dto.Club.PostClubDTO;
import com.MaFederation.MaFederation.dto.Club.ResponseClubDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.services.ClubServices;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/clubs")
public class ClubController {
    
    private final ClubServices clubServices;

    public ClubController(ClubServices clubServices) {
        this.clubServices = clubServices;
    }
    
    // Create a new club
    @PostMapping("/register")
    public Club registerClub(@RequestBody PostClubDTO clubDto) {
        return clubServices.addClub(clubDto);
    }

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

    // Get all members of a specific club
    @GetMapping("/{id}/members")
    public List<ResponceClubMemberDTO> getMembers(@PathVariable Integer id) {
        return clubServices.getMembers(id);
    }
}
