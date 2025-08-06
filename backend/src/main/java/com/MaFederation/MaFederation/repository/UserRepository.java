package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Tu peux ajouter des méthodes personnalisées ici si besoin, par exemple :
    boolean existsByEmail(String email);
    User findByFiles_Id(Integer clubFileId);
    

    User findByEmail(String email);
}
