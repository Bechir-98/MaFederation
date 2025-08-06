package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.model.UserFile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Integer> {

      List<UserFile> findByUser(User user);
}
