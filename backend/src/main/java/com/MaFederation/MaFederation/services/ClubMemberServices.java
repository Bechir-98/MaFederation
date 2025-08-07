package com.MaFederation.MaFederation.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.repository.ClubMemberRepository;

@Service
public class ClubMemberServices {

    private final ClubMemberRepository clubMemberRepository;

    public ClubMemberServices(ClubMemberRepository clubMemberRepository) {
        this.clubMemberRepository = clubMemberRepository;
    }


}
