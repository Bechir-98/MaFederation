package com.MaFederation.MaFederation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubFile;
import org.springframework.stereotype.Repository;


@Repository
public interface ClubFileRepository extends JpaRepository<ClubFile, Integer> {
    // Custom query methods can be defined here if needed
    List<ClubFile> findByClub(Club club);
    
}



