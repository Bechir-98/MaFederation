package com.MaFederation.MaFederation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.services.ClubServices;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/clubs/{clubId}/members")
public class ClubMemberController {

    private final ClubServices clubServices;

    @Autowired
    public ClubMemberController(ClubServices clubServices) {
        this.clubServices = clubServices;
    }

    // Create and add member to club
    @PostMapping("/add")
    public ClubMember addClubMemberToClub(
            @RequestBody PostClubMemberDTO memberDTO,
            @PathVariable Integer clubId) {
        return clubServices.createMemberFromClub(memberDTO, clubId);
    }

    // Get a specific club member by ID
    @GetMapping("/{memberId}")
    public ResponceClubMemberDTO getClubMemberById(
            @PathVariable Integer clubId,
            @PathVariable Integer memberId) {
        return clubServices.getClubMember(memberId, clubId);
    }

    // Get all members of a club
    @GetMapping
    public List<ResponceClubMemberDTO> getAllMembers(@PathVariable Integer clubId) {
        return clubServices.getMembers(clubId);
    }
}
