package com.MaFederation.MaFederation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MaFederation.MaFederation.dto.Club.ClubDTO;
import com.MaFederation.MaFederation.mappers.ClubMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubFile;
import com.MaFederation.MaFederation.repository.ClubFileRepository;
import com.MaFederation.MaFederation.repository.ClubRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubServices {

    private final ClubRepository      clubRepository;
    private final ClubMapper          clubMapper;
    private final ClubFileRepository  clubFileRepository;

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

        // ✅ Attach files to the club entity
        if (clubDto.fileIds() != null && !clubDto.fileIds().isEmpty()) {
            List<ClubFile> files = clubFileRepository.findAllById(clubDto.fileIds());

            if (files.size() != clubDto.fileIds().size()) {
                throw new EntityNotFoundException("One or more club file IDs are invalid.");
            }

            // Set club reference in each file (to persist the owning side)
            files.forEach(file -> file.setClub(club));
            club.setFiles(files);
        }

        return clubRepository.save(club);
    }

    /** Récupérer un club par ID (DTO) */
    public ClubDTO getClubById(int id) {
        Club club = clubRepository.findById(id)
                                  .orElseThrow(() ->
                                       new RuntimeException("Club not found with id: " + id));
        return clubMapper.toDto(club);
    }

    /** Récupérer un club par ID (Entity) */
    public Club getClub(Integer clubId) {
        return clubRepository.findById(clubId)
                             .orElseThrow(() ->
                                  new RuntimeException("Club not found with id: " + clubId));
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







   









}
