package com.MaFederation.MaFederation.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.Club.ClubDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubFile;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.repository.CategoryRepository;
import com.MaFederation.MaFederation.repository.ClubFileRepository;
import com.MaFederation.MaFederation.repository.ClubMemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubMapper {

    private final CategoryRepository categoryRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubFileRepository clubFileRepository;

    /*------------------------------------------------------
     * Entity ➜ DTO
     *-----------------------------------------------------*/
    public ClubDTO toDto(Club club) {
        if (club == null) return null;

        // Categories → IDs uniquement
        List<Integer> categoryIds = club.getCategories() == null ? new ArrayList<>()
            : club.getCategories()
                  .stream()
                  .map(Category::getCategoryId)
                  .collect(Collectors.toList());

        // Members → IDs uniquement
        List<Integer> memberIds = club.getMembers() == null ? new ArrayList<>()
            : club.getMembers()
                  .stream()
                  .map(ClubMember::getUserId)
                  .collect(Collectors.toList());

        // Files → IDs uniquement
        List<Integer> fileIds = club.getFiles() == null ? new ArrayList<>()
            : club.getFiles()
                  .stream()
                  .map(ClubFile::getId)
                  .collect(Collectors.toList());

        return new ClubDTO(
            club.getClubID(),
            club.getName(),
            club.getLocation(),
            club.getFoundedYear(),
            club.getContactEmail(),
            club.getContactPhone(),
            club.getBankAccount(),
            club.getBankName(),
            categoryIds,
            memberIds,
            fileIds
        );
    }

    /*------------------------------------------------------
     * DTO ➜ Entity
     *-----------------------------------------------------*/
    public Club fromDto(ClubDTO dto) {
        if (dto == null) return null;

        Club club = new Club();

        club.setClubID(dto.clubId());
        club.setName(dto.name());
        club.setLocation(dto.location());
        club.setFoundedYear(dto.foundedYear());
        club.setContactEmail(dto.contactEmail());
        club.setContactPhone(dto.contactPhone());
        club.setBankAccount(dto.bankAccount());
        club.setBankName(dto.bankName());

        // Files
        if (dto.fileIds() != null && !dto.fileIds().isEmpty()) {
            List<ClubFile> files = clubFileRepository.findAllById(dto.fileIds());
            if (files.size() != dto.fileIds().size()) {
                throw new EntityNotFoundException("One or more club file IDs are invalid");
            }
            club.setFiles(files);
        } else {
            club.setFiles(new ArrayList<>());
        }

        // Categories
        if (dto.categoryIds() != null && !dto.categoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dto.categoryIds());
            if (categories.size() != dto.categoryIds().size()) {
                throw new EntityNotFoundException("One or more category IDs are invalid");
            }
            club.setCategories(categories);
        } else {
            club.setCategories(new ArrayList<>());
        }

        // Members
        if (dto.memberIds() != null && !dto.memberIds().isEmpty()) {
            List<ClubMember> members = dto.memberIds().stream()
                .map(id -> clubMemberRepository.findById(id)
                      .orElseThrow(() -> new EntityNotFoundException("Member not found: " + id)))
                .collect(Collectors.toList());
            club.setMembers(members);
        } else {
            club.setMembers(new ArrayList<>());
        }

        return club;
    }
}
