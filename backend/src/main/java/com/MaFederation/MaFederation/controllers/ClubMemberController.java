package com.MaFederation.MaFederation.controllers;

import org.springframework.web.bind.annotation.*;
import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.services.ClubMemberServices;
import com.MaFederation.MaFederation.services.ClubServices;


@RestController
@CrossOrigin("*")
public class ClubMemberController {

    private final ClubServices clubServices;
    private final ClubMemberServices clubMemberServices;

    public ClubMemberController(ClubServices clubServices,ClubMemberServices clubMemberServices) {
        this.clubServices = clubServices;
        this.clubMemberServices=clubMemberServices;
    }

    //create a new club member Alone
    @PostMapping("/addmember")
    public ClubMember createMember(@RequestBody PostClubMemberDTO memberDTO) {
        return clubMemberServices.createMember(memberDTO);
    }



    // // Create and add member from club
    // @PostMapping("/clubs/{clubId}/members/add")
    // public ClubMember addClubMemberToClub(
    //         @RequestBody PostClubMemberDTO memberDTO,
    //         @PathVariable Integer clubId) {
    //     return clubServices.createMemberFromClub(memberDTO, clubId);
    // }

}

    
