package com.MaFederation.MaFederation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MaFederation.MaFederation.model.Administration;
import org.springframework.stereotype.Repository;


@Repository

public interface AdminRepository extends JpaRepository<Administration, Long> {}
