package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

    // Optional: find moderator by email
    Optional<Moderator> findByEmail(String email);

    // Optional: check if email exists
    boolean existsByEmail(String email);
}
