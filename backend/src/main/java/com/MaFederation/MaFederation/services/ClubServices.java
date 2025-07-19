package com.MaFederation.MaFederation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MaFederation.MaFederation.dto.ClubDTO;
import com.MaFederation.MaFederation.mappers.CategoryMapper;
import com.MaFederation.MaFederation.mappers.ClubMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.repository.ClubRepository;

@Service
@Transactional(readOnly = true)
public class ClubServices {

    private final ClubRepository clubRepository;
    private final ClubMapper     clubMapper;

    public ClubServices(ClubRepository clubRepository,
                        ClubMapper clubMapper,
                        CategoryMapper categoryMapper) {
        this.clubRepository  = clubRepository;
        this.clubMapper      = clubMapper;
    }

    /* ----------------------------------------------------------------------
     * CRUD de base
     * -------------------------------------------------------------------- */

    /** Récupérer tous les clubs (DTO) */
    public List<ClubDTO> getAllClubs() {
        return clubRepository.findAll()
                             .stream()
                             .map(clubMapper::toDto)
                             .collect(Collectors.toList());
    }

    /** Ajouter un club depuis un DTO */
    @Transactional
    public Club addClub(ClubDTO clubDto) {
        Club club = clubMapper.fromDto(clubDto);
        return clubRepository.save(club);
    }

    /** Récupérer un club par ID (DTO) */
    public ClubDTO getClubById(int id) {
        Club club = clubRepository.findById(id)
                                  .orElseThrow(() ->
                                       new RuntimeException("Club not found with id: " + id));
        return clubMapper.toDto(club);
    }

    /** Supprimer tous les clubs */
    @Transactional
    public void deleteAll() {
        clubRepository.deleteAll();
    }

    /** Supprimer un club par ID */
    @Transactional
    public void deleteById(int id) {
        clubRepository.deleteById(id);
    }

    /* ----------------------------------------------------------------------
     * Méthodes spécifiques
     * -------------------------------------------------------------------- */

   




}
