package com.MaFederation.MaFederation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MaFederation.MaFederation.dto.Club.ClubDTO;
import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;
import com.MaFederation.MaFederation.mappers.ClubMapper;
import com.MaFederation.MaFederation.mappers.ClubMemberMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubFile;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.repository.ClubFileRepository;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.ClubMemberRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClubServices {
    private final ClubRepository clubRepository;
    private final ClubMemberMapper clubMemberMapper;
    private final ClubMapper clubMapper;
    private final ClubFileRepository clubFileRepository;
    private final ClubMemberRepository clubMemberRepository;

    public ClubServices(ClubRepository clubRepository, ClubMemberMapper clubMemberMapper,
                         ClubMapper clubMapper, ClubFileRepository clubFileRepository,
                         ClubMemberRepository clubMemberRepository) {
        this.clubRepository = clubRepository;
        this.clubMemberMapper = clubMemberMapper;
        this.clubMapper = clubMapper;
        this.clubFileRepository = clubFileRepository;
        this.clubMemberRepository = clubMemberRepository;
    }



    /** Get all clubs as DTOs */
    public List<ClubDTO> getAllClubs() {
        return clubRepository.findAll()
                             .stream()
                             .map(clubMapper::toDto)
                             .collect(Collectors.toList());
    }

    /** Add a new club from DTO */
    @Transactional
    public Club addClub(ClubDTO clubDto) {
        Club club = clubMapper.fromDto(clubDto);

        if (clubDto.fileIds() != null && !clubDto.fileIds().isEmpty()) {
            List<ClubFile> files = clubFileRepository.findAllById(clubDto.fileIds());

            if (files.size() != clubDto.fileIds().size()) {
                throw new EntityNotFoundException("One or more club file IDs are invalid.");
            }

            files.forEach(file -> file.setClub(club));
            club.setFiles(files);
        }

        return clubRepository.save(club);
    }

    /** Get a club DTO by ID */
    public ClubDTO getClubById(int id) {
        Club club = clubRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("Club not found with id: " + id));
        return clubMapper.toDto(club);
    }

    /** Get Club Entity */
    public Club getClub(Integer clubId) {
        return clubRepository.findById(clubId)
                             .orElseThrow(() -> new RuntimeException("Club not found with id: " + clubId));
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
    public List<ResponceClubMemberDTO> getMembers(Integer id) {
        Club club = clubRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("Club not found with id: " + id));

        return club.getMembers().stream()
                   .map(clubMemberMapper::toResponseDto)
                   .collect(Collectors.toList());
    }

    /** Get a single member by club + member ID */
    public ResponceClubMemberDTO getClubMember(Integer memberId, Integer clubId) {
        Club club = clubRepository.findById(clubId)
                                  .orElseThrow(() -> new RuntimeException("Club not found with id: " + clubId));

        return club.getMembers().stream()
                   .filter(member -> member.getUserId().equals(memberId))
                   .findFirst()
                   .map(clubMemberMapper::toResponseDto)
                   .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
    }
}
