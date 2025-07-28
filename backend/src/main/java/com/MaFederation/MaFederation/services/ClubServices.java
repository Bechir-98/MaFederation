package com.MaFederation.MaFederation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MaFederation.MaFederation.dto.Club.PostClubDTO;
import com.MaFederation.MaFederation.dto.Club.ResponseClubDTO;
import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;
import com.MaFederation.MaFederation.mappers.ClubMapper;
import com.MaFederation.MaFederation.mappers.ClubMemberMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.ClubMemberRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClubServices {

    private final ClubRepository clubRepository;
    private final ClubMemberMapper clubMemberMapper;
    private final ClubMapper clubMapper;
    private final ClubMemberRepository clubMemberRepository;

    public ClubServices(ClubRepository clubRepository,
                        ClubMemberMapper clubMemberMapper,
                        ClubMapper clubMapper,
                        ClubMemberRepository clubMemberRepository) {
        this.clubRepository = clubRepository;
        this.clubMemberMapper = clubMemberMapper;
        this.clubMapper = clubMapper;
        this.clubMemberRepository = clubMemberRepository;
    }

    /** Get all clubs as Response DTOs */
    public List<ResponseClubDTO> getAllClubs() {
        return clubRepository.findAll()
                .stream()
                .map(clubMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /** Add a new club from Post DTO */
    @Transactional
    public Club addClub(PostClubDTO clubDto) {
        Club club = clubMapper.fromDto(clubDto);
        return clubRepository.save(club);
    }

    /** Get a club Response DTO by ID */
    public ResponseClubDTO getClubById(int id) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Club not found with id: " + id));
        return clubMapper.toResponseDto(club);
    }

    /** Get Club Entity by ID */
    public Club getClub(Integer clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("Club not found with id: " + clubId));
    }

    /** Delete all clubs */
    @Transactional
    public void deleteAll() {
        clubRepository.deleteAll();
    }

    /** Delete a club by ID */
    @Transactional
    public void deleteById(int id) {
        clubRepository.deleteById(id);
    }

    /** Create and attach a member to a club */
    @Transactional
    public ClubMember createMemberFromClub(PostClubMemberDTO memberDTO, Integer clubID) {
        if (clubID == null) {
            throw new IllegalArgumentException("Club ID cannot be null when creating a member from club.");
        }
        memberDTO.setClubId(clubID);
        ClubMember member = clubMemberMapper.toEntity(memberDTO);
        return clubMemberRepository.save(member);
    }

    /** Get all members of a club */
    public List<ResponceClubMemberDTO> getMembers(Integer clubId) {
        Club club = getClub(clubId);
        return club.getMembers().stream()
                .map(clubMemberMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /** Get a single member by club + member ID */
    public ResponceClubMemberDTO getClubMember(Integer memberId, Integer clubId) {
        Club club = getClub(clubId);
        return club.getMembers().stream()
                .filter(member -> member.getUserId().equals(memberId))
                .findFirst()
                .map(clubMemberMapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
    }
}
