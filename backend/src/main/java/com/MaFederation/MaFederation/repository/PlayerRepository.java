package com.MaFederation.MaFederation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MaFederation.MaFederation.model.Player;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    
}
