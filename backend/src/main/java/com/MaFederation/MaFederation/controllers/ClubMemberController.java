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
public class ClubMemberController {

    private final ClubServices clubServices;

    public ClubMemberController(ClubServices clubServices) {
        this.clubServices = clubServices;
    }

    // Create and add member to club
    @PostMapping("/clubs/{clubId}/members/add")
    public ClubMember addClubMemberToClub(
            @RequestBody PostClubMemberDTO memberDTO,
            @PathVariable Integer clubId) {
        return clubServices.createMemberFromClub(memberDTO, clubId);
    }

}

    
