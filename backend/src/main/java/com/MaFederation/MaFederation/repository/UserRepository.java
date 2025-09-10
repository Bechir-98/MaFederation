package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    User findByFiles_Id(Integer clubFileId);
    Optional<User> findByEmail(String email);
}
