package com.MaFederation.MaFederation.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.MaFederation.MaFederation.enums.FileType;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubFile;
import com.MaFederation.MaFederation.repository.ClubFileRepository;
import com.MaFederation.MaFederation.repository.ClubRepository;

@Service
public class ClubFilesServices {

    private final ClubFileRepository clubFileRepository;
    private final ClubRepository clubRepository;

    public ClubFilesServices(ClubFileRepository clubFileRepository, ClubRepository clubRepository) {
        this.clubFileRepository = clubFileRepository;
        this.clubRepository = clubRepository;
    }

    

   public void saveFile(Integer clubId, MultipartFile file, FileType type) throws IOException {
    Club club = clubRepository.findById(clubId)
        .orElseThrow(() -> new RuntimeException("Club not found"));

    ClubFile clubFile = ClubFile.builder()
        .type(type)
        .content(file.getBytes()) 
        .club(club)
        .build();

    clubFileRepository.save(clubFile);
}


}
