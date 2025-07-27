package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Integer> {

  
}
