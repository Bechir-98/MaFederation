package com.MaFederation.MaFederation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.MaFederation.MaFederation.model.Club;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface ClubRepository extends JpaRepository <Club,Integer> {

    Club findByFiles_Id(Integer clubFileId);

}
