package com.MaFederation.MaFederation.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.mappers.ClubMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.repository.clubRepository;
import com.MaFederation.MaFederation.dto.ClubDTO;


@Service
public class ClubServices {

    public final clubRepository clubrepository;
    public final ClubMapper clubmapper;
/////////////////////////////////////////////////
    public ClubServices ( clubRepository clubrepository , ClubMapper clubmapper )

    {
    this.clubrepository=clubrepository;
    this.clubmapper=clubmapper;
    }
/////////////////////////////////////////////////
    public List<ClubDTO> getAllClubs( )
    {
        return clubrepository.findAll().stream ().map(clubmapper::toDto).toList();

    }
    /////////////////////////////////////////////////

    public Club addClub ( ClubDTO clubdto  )
    {
       Club club = clubmapper.fromDto(clubdto);

        return clubrepository.save(club);

    }
    //////////////////////////////////////////////////// 
    
  public ClubDTO getClubById (int id) {
    Club club = clubrepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Club not found with id: " + id));
        
    return clubmapper.toDto(club);
}


public void DeleteAll( )
{

    clubrepository.deleteAll();
}



public void DeleteById ( int id )
{
    clubrepository.deleteById(id);

}


    
}
