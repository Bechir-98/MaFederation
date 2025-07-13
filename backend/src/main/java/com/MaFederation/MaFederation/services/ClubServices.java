package com.MaFederation.MaFederation.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.mappers.ClubMapper;
import com.MaFederation.MaFederation.model.ClubRepresentation;
import com.MaFederation.MaFederation.repository.clubRepository;
import com.MaFederation.MaFederation.dto.clubDTO;


@Service
public class ClubServices {

    public final clubRepository clubrepository;
    public final ClubMapper clubmapper;

    public ClubServices ( clubRepository clubrepository , ClubMapper clubmapper )

    {
    this.clubrepository=clubrepository;
    this.clubmapper=clubmapper;
    }

    public List<clubDTO> getAllClubs( )
    {
        return clubrepository.findAll().stream ().map(clubmapper::toDto).toList();

    }

    public ClubRepresentation addClub ( clubDTO clubdto  )
    {
       ClubRepresentation club = clubmapper.fromDto(clubdto);

        return clubrepository.save(club);

    }

    
}
