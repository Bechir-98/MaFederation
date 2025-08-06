package com.MaFederation.MaFederation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MaFederation.MaFederation.dto.Admin.ResponseAdministrationDTO;
import com.MaFederation.MaFederation.dto.Category.CategoryDTO;
import com.MaFederation.MaFederation.dto.Club.PostClubDTO;
import com.MaFederation.MaFederation.dto.Club.ResponseClubDTO;
import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
import com.MaFederation.MaFederation.dto.Player.ResponsePlayerDTO;
import com.MaFederation.MaFederation.dto.Staff.ResponseStaffDTO;
import com.MaFederation.MaFederation.mappers.AdministrationMapper;
import com.MaFederation.MaFederation.mappers.CategoryMapper;
import com.MaFederation.MaFederation.mappers.ClubMapper;
import com.MaFederation.MaFederation.mappers.ClubMemberMapper;
import com.MaFederation.MaFederation.mappers.PlayerMapper;
import com.MaFederation.MaFederation.mappers.StaffMapper;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.CategoryRepository;
import com.MaFederation.MaFederation.repository.ClubMemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubServices {

    private final ClubRepository clubRepository;
    private final CategoryRepository categoryRepository;
    private final ClubMemberMapper clubMemberMapper;
    private final ClubMapper clubMapper;
    private final ClubMemberRepository clubMemberRepository;
     private final CategoryMapper categoryMapper;
    private final StaffMapper staffMapper;
    private final PlayerMapper playerMapper;
    private final AdministrationMapper administrationMapper;


    /** Get all clubs as Response DTOs */
    public List<ResponseClubDTO> getAllClubs() {



        return clubRepository.findAll()
                .stream()
                .map(clubMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /** Add a new club from Post DTO */
    @Transactional
    public ResponseClubDTO addClub(PostClubDTO clubDto) {
    Club club = clubMapper.fromDto(clubDto);
    Club savedClub = clubRepository.save(club);
    clubRepository.flush();
    return clubMapper.toResponseDto(savedClub);

}//////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////
                            /** Get a club Response DTO by ID */
    @Transactional
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
////////////////////////////////////////////////////////////////////////////////////////////////////
  

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
 ////////////////////////////////////////////////////////////////////////////////////////////////////   
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
    public List<ResponseClubMemberDTO> getMembers(Integer clubId) {
        Club club = getClub(clubId);
        return club.getMembers().stream()
                .map(clubMemberMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /** Get a single member by club + member ID */
    @Transactional
    public ResponseClubMemberDTO getClubMember( Integer clubId,Integer memberId) {
        Club club = getClub(clubId);
        return club.getMembers().stream()
                .filter(member -> member.getId().equals(memberId))
                .findFirst()
                .map(clubMemberMapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
    }




                ///////////////////////////CATEGORIES RELATED ///////////////////////////////////////

    public List<CategoryDTO> getCategoriesByClubId(Integer clubId) {
    if (clubId == null) {
        throw new IllegalArgumentException("Club ID must not be null.");
    }

    List<Category> categories = clubRepository.findById(clubId)
            .map(Club::getCategories)
            .orElseThrow(() -> new EntityNotFoundException("Club not found with id: " + clubId));

    return categories.stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
}

public CategoryDTO addCategoryToClub(Integer clubId, Integer categoryId) {
    if (clubId == null || categoryId == null) {
        throw new IllegalArgumentException("Club ID and Category ID must not be null.");
    }

    Club club = clubRepository.findById(clubId)
            .orElseThrow(() -> new EntityNotFoundException("Club not found with id: " + clubId));

    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

    // Prevent duplicate association
    if (club.getCategories().contains(category)) {
        throw new IllegalStateException("Category already associated with this club.");
    }

    // Associate and save
    club.getCategories().add(category);
    clubRepository.save(club);

    // Return DTO
    return categoryMapper.toDto(category);
}


 public void removeCategoryFromClub(Integer clubId, Integer categoryId) {
        Club club = clubRepository.findById(clubId)
            .orElseThrow(() -> new EntityNotFoundException("Club not found with id: " + clubId));

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        if (!club.getCategories().contains(category)) {
            throw new IllegalStateException("Category is not associated with the club.");
        }

        club.getCategories().remove(category);
        clubRepository.save(club);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   

    public List<ResponseStaffDTO> getStaffByClubId(Integer clubId) {
        Club club = clubRepository.findById(clubId)
            .orElseThrow(() -> new RuntimeException("Club not found"));

        return club.getMembers().stream()
            .filter(Staff.class::isInstance) // safely filter only Staff
            .map(Staff.class::cast)          // explicitly cast to Staff
            .map(staffMapper::toDto)         // now Java knows it's Staff → ResponseStaffDTO
            .collect(Collectors.toList());
    }

public List<ResponseAdministrationDTO> getAdministrationByClubId(Integer clubId) {
    Club club = clubRepository.findById(clubId)
        .orElseThrow(() -> new RuntimeException("Club not found"));

    return club.getMembers().stream()
        .filter(Administration.class::isInstance)
        .map(Administration.class::cast)
        .map(administrationMapper::toDto)
        .collect(Collectors.toList());
}

public List<ResponsePlayerDTO> getPlayersByClubId(Integer clubId) {
    Club club = clubRepository.findById(clubId)
        .orElseThrow(() -> new RuntimeException("Club not found"));

    return club.getMembers().stream()
        .filter(Player.class::isInstance)
        .map(Player.class::cast)
        .map(playerMapper::toDto)
        .collect(Collectors.toList());
}



}





