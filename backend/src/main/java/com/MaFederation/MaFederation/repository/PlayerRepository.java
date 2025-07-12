package com.MaFederation.MaFederation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MaFederation.MaFederation.model.PlayerRepresenation;

public interface PlayerRepository extends JpaRepository<PlayerRepresenation,Integer> {

    
}
