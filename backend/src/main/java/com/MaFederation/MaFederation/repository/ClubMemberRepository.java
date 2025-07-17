package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Integer> {
    // Add custom queries here if needed
}
