package com.MaFederation.MaFederation.controllers.Club;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.MaFederation.MaFederation.dto.Club.ClubDTO;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.services.ClubServices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@CrossOrigin( "http://localhost:4200/")
public class ClubController {

    public final ClubServices clubservices;

    public ClubController( ClubServices clubservices)
    {
        this.clubservices=clubservices;
    }

    //Create
      @PostMapping("/clubs/register-club")
    public Club postMethodName(@RequestBody ClubDTO club) {
        
        return this.clubservices.addClub(club);
    }

    
    //Get
    @GetMapping("/clubs")
    public List getMethodName() {
        return this.clubservices.getAllClubs();
    }

    
    @GetMapping("/club/{id}")
    public ClubDTO getMethodName(@PathVariable int id ) {
        return this.clubservices.getClubById(id );
    }
    


    // //del
    // @DeleteMapping("delete/club/{id}")
    // public void DeleteByID (@PathVariable int id  )
    // {
    //       this.clubservices.DeleteById(id); 
    // }





    // @DeleteMapping("/delete/clubs")
    // public void DeleteAllclubs ( )
    // {
    //     this.clubservices.DeleteAll();

    // }



    





}
