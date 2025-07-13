package com.MaFederation.MaFederation.controllers.Club;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.MaFederation.MaFederation.dto.clubDTO;
import com.MaFederation.MaFederation.mappers.ClubMapper;
import com.MaFederation.MaFederation.model.ClubRepresentation;
import com.MaFederation.MaFederation.services.ClubServices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin( "http://localhost:4200/")
public class clubController {

    public final ClubServices clubservices;

    public clubController( ClubServices clubservices)
    {
        this.clubservices=clubservices;
    }


    @GetMapping("/clubs")
    public List getMethodName() {
        return this.clubservices.getAllClubs();
    }
    
    
    @PostMapping("/addclub")
    public ClubRepresentation postMethodName(@RequestBody clubDTO club) {
        
        return this.clubservices.addClub(club);
    }
    

    





}
