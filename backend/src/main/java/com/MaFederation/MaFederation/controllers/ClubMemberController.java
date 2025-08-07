package com.MaFederation.MaFederation.controllers;


import org.springframework.web.bind.annotation.*;

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

}

    
