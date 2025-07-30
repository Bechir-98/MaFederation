package com.MaFederation.MaFederation.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.MaFederation.MaFederation.dto.Club.ClubFileContent;
import com.MaFederation.MaFederation.dto.Club.ClubFileDTO;
import com.MaFederation.MaFederation.enums.ClubFileType;
import com.MaFederation.MaFederation.mappers.ClubFileMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubFile;
import com.MaFederation.MaFederation.repository.ClubFileRepository;
import com.MaFederation.MaFederation.repository.ClubRepository;

import jakarta.transaction.Transactional;

@Service
public class ClubFilesServices {

    private final ClubFileRepository clubFileRepository;
    private final ClubRepository clubRepository;
    private final ClubFileMapper clubFileMapper;

    public ClubFilesServices(ClubFileRepository clubFileRepository, ClubRepository clubRepository,ClubFileMapper clubFileMapper) {
        this.clubFileRepository = clubFileRepository;
        this.clubRepository = clubRepository;
        this.clubFileMapper=clubFileMapper;
    }

    

   public void saveFile(Integer clubId, MultipartFile file, String typeStr) throws IOException {
    Club club = clubRepository.findById(clubId)
        .orElseThrow(() -> new RuntimeException("Club not found"));

    ClubFileType type;
    try {
        type = ClubFileType.valueOf(typeStr.toUpperCase());
    } catch (IllegalArgumentException ex) {
        throw new IllegalArgumentException("Invalid file type: " + typeStr);
    }

    ClubFile clubFile = ClubFile.builder()
        .type(type)
        .content(file.getBytes())
        .club(club)
        .build();

    clubFileRepository.save(clubFile);
}

@Transactional
public List<ClubFileDTO> getFilesByClubId(Integer clubId) {
    Club club = clubRepository.findById(clubId)
        .orElseThrow(() -> new RuntimeException("Club not found"));

    return clubFileRepository.findByClub(club).stream()
        .map(clubFileMapper::toDto)
        .toList();
}

@Transactional
public ClubFileContent getFileContent(Integer fileId) {
    ClubFile clubFile = clubFileRepository.findById(fileId)
        .orElseThrow(() -> new RuntimeException("File not found"));

    return new ClubFileContent(
        clubFile.getContent()
    );
}

    @Transactional
public ClubFileDTO updateFile(Integer fileId, MultipartFile file) {
    Club club = clubRepository.findByFiles_Id(fileId);
    if (club == null) {
        throw new RuntimeException("Club not found for the file id");
    }

    ClubFile clubFile = clubFileRepository.findById(fileId)
        .orElseThrow(() -> new RuntimeException("File not found"));

    try {
        clubFile.setContent(file.getBytes());
    } catch (IOException e) {
        throw new RuntimeException("Failed to read file content", e);
    }

    clubFile.setClub(club);
    clubFileRepository.save(clubFile);

    return clubFileMapper.toDto(clubFile);
}

 
public void deleteFile(Integer fileId) {
    if (!clubFileRepository.existsById(fileId)) {
        throw new RuntimeException("File not found with ID: " + fileId);
    }
    clubFileRepository.deleteById(fileId);
}

}


