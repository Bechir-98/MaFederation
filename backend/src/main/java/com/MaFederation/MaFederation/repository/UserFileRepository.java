package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Integer> {

    // Récupérer tous les fichiers d’un utilisateur donné
    List<UserFile> findByUser_UserId(Integer userId);

    // Supprimer tous les fichiers d’un utilisateur
    void deleteByUser_UserId(Integer userId);
}
