package com.MaFederation.MaFederation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.MaFederation.MaFederation.model.Club;
import org.springframework.stereotype.Repository;


@Repository
public interface ClubRepository extends JpaRepository <Club,Integer> {

    Club findByFiles_Id(Integer clubFileId);

}
