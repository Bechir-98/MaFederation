package com.MaFederation.MaFederation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.MaFederation.MaFederation.dto.Club.ClubDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.services.ClubServices;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/clubs")
public class ClubController {
    
    private ClubServices clubServices;
    public ClubController(ClubServices clubServices) {
        this.clubServices = clubServices;
    }
    
    // Create a new club
    @PostMapping("/register")
    public Club registerClub(@RequestBody ClubDTO clubDto) {
        return clubServices.addClub(clubDto);
    }

    // Get all clubs
    @GetMapping
    public List<ClubDTO> getAllClubs() {
        return clubServices.getAllClubs();
    }

    // Get one club by ID
    @GetMapping("/{id}")
    public ClubDTO getClubById(@PathVariable int id) {
        return clubServices.getClubById(id);
    }

    // Get all members of a specific club
    @GetMapping("/{id}/members")
    public List<ResponceClubMemberDTO> getMembers(@PathVariable Integer id) {
        return clubServices.getMembers(id);
    }

    // Optional: Uncomment if needed for full CRUD
    /*
    @DeleteMapping("/{id}")    @RestController
    @RequestMapping("/api/clubs")
    public class ClubController {
        
        @Autowired
        private ClubServices clubServices;
        
        // Use field injection instead of constructor injection
        // Remove any constructor and let Spring handle injection
        
        // ...existing code...
    }
    public void deleteClubById(@PathVariable int id) {
        clubServices.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllClubs() {
        clubServices.deleteAll();
    }
    */
}
