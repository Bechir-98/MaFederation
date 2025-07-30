package com.MaFederation.MaFederation.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
import com.MaFederation.MaFederation.mappers.ClubMemberMapper;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.repository.ClubMemberRepository;

@Service
public class ClubMemberServices {

    private final ClubMemberRepository clubMemberRepository;
    private final ClubMemberMapper clubMemberMapper;

    public ClubMemberServices(ClubMemberRepository clubMemberRepository, ClubMemberMapper clubMemberMapper) {
        this.clubMemberRepository = clubMemberRepository;
        this.clubMemberMapper = clubMemberMapper;
    }

   @Transactional
public ResponseClubMemberDTO createMember(PostClubMemberDTO memberDTO) {
    if (memberDTO == null) {
        throw new IllegalArgumentException("Member DTO cannot be null");
    }

    ClubMember member = clubMemberRepository.save(clubMemberMapper.toEntity(memberDTO));
    return clubMemberMapper.toResponseDto(member);
}

    public ClubMember getMemberById(Integer id) {
        return clubMemberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Club member not found with id: " + id));
    }
}
