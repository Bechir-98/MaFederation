package com.MaFederation.MaFederation.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.ClubDTO;
import com.MaFederation.MaFederation.mappers.ClubMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.repository.clubRepository;

@Service
public class ClubServices {

    private final clubRepository clubrepository;
    private final ClubMapper clubmapper;

    // Injection par constructeur (ajoute @Autowired si tu n'utilises pas Lombok)
    public ClubServices(clubRepository clubrepository, ClubMapper clubmapper) {
        this.clubrepository = clubrepository;
        this.clubmapper = clubmapper;
    }

    // Récupérer tous les clubs en DTO
    public List<ClubDTO> getAllClubs() {
        return clubrepository.findAll()
                .stream()
                .map(clubmapper::toDto)
                .toList();
    }

    // Ajouter un club à partir d'un DTO
    public Club addClub(ClubDTO clubdto) {
        Club club = clubmapper.fromDto(clubdto);
        return clubrepository.save(club);
    }

    // Récupérer un club par ID en DTO
    public ClubDTO getClubById(int id) {
        Club club = clubrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found with id: " + id));
        return clubmapper.toDto(club);
    }

    // Supprimer tous les clubs
    public void deleteAll() {
        clubrepository.deleteAll();
    }

    // Supprimer un club par ID
    public void deleteById(int id) {
        clubrepository.deleteById(id);
    }
}
