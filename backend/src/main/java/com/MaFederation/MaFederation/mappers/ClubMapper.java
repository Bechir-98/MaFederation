package com.MaFederation.MaFederation.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.ClubDTO;
import com.MaFederation.MaFederation.dto.ClubFileDTO;
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

    private final CategoryRepository   categoryRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubFileRepository   clubFileRepository;

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

        // Members → uniquement les IDs des membres
        List<Integer> memberIds = club.getMembers() == null ? new ArrayList<>()
            : club.getMembers()
                  .stream()
                  .map(ClubMember::getUserId)
                  .collect(Collectors.toList());

        // Fichier club
        ClubFile file = club.getFiles();
        ClubFileDTO fileDto = null;
        if (file != null) {
            fileDto = new ClubFileDTO(
                file.getId(),
                file.getLicenseUrl(),
                file.getLogoUrl()
            );
        }

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
            fileDto
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

        // ClubFile mapping
        if (dto.files() != null) {
            ClubFileDTO fDto = dto.files();
            ClubFile file = (fDto.id() != null)
                ? clubFileRepository.findById(fDto.id()).orElse(new ClubFile())
                : new ClubFile();
            file.setLicenseUrl(fDto.licenseUrl());
            file.setLogoUrl(fDto.logoUrl());
            club.setFiles(file);
        }

        // Categories (à partir des IDs)
        if (dto.categoryIds() != null && !dto.categoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dto.categoryIds());
            if (categories.size() != dto.categoryIds().size()) {
                throw new EntityNotFoundException("One or more category IDs are invalid");
            }
            club.setCategories(categories);
        } else {
            club.setCategories(new ArrayList<>());
        }

        // Membres (à partir des IDs)
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
