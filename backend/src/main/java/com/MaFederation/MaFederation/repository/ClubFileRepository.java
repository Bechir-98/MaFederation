package com.MaFederation.MaFederation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.MaFederation.MaFederation.model.ClubFile;
import org.springframework.stereotype.Repository;


@Repository
public interface ClubFileRepository extends JpaRepository<ClubFile, Integer> {}



