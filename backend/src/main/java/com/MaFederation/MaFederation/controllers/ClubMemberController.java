package com.MaFederation.MaFederation.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
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
    public ResponseClubMemberDTO createMember(
    @RequestPart("member") PostClubMemberDTO memberDTO,
    @RequestPart(value = "profilePicture") MultipartFile profilePicture
) throws IOException {
    if (profilePicture != null && !profilePicture.isEmpty()) {
        memberDTO.setProfilePicture(profilePicture.getBytes());
    }
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

    
