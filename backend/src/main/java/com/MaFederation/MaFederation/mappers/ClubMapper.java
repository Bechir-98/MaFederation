package com.MaFederation.MaFederation.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.CategoryDTO;
import com.MaFederation.MaFederation.dto.ClubDTO;
import com.MaFederation.MaFederation.dto.ClubFileDTO;
import com.MaFederation.MaFederation.dto.ClubMemberDTO;
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
    private final ClubFileRepository clubfileRepository;

    public ClubDTO toDto(Club club) {
        if (club == null) return null;

        List<CategoryDTO> categories = club.getCategories().stream()
            .map(c -> new CategoryDTO(
                c.getCategoryId(),
                c.getName(),
                c.getDescription(),
                c.getAgeMin(),
                c.getAgeMax()
            ))
            .collect(Collectors.toList());

        List<ClubMemberDTO> members = club.getMembers().stream()
            .map(m -> new ClubMemberDTO(
                m.getUserId(),
                m.getPosition(),
                m.getClub().getClubID()
            ))
            .collect(Collectors.toList());

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
            categories,
            members,
            fileDto
        );
    }

    public Club fromDto(ClubDTO clubDto) {
        if (clubDto == null) return null;

        Club club = new Club();

        // Si ID existe (mise à jour), sinon création (ID null)
        club.setClubID(clubDto.clubId());

        club.setName(clubDto.name());
        club.setLocation(clubDto.location());
        club.setFoundedYear(clubDto.foundedYear());
        club.setContactEmail(clubDto.contactEmail());
        club.setContactPhone(clubDto.contactPhone());
        club.setBankAccount(clubDto.bankAccount());
        club.setBankName(clubDto.bankName());

        // Gestion du ClubFile
        if (clubDto.files() != null) {
            ClubFileDTO fileDto = clubDto.files();
            ClubFile file;
            if (fileDto.id() != null) {
                // Mise à jour fichier existant
                file = clubfileRepository.findById(fileDto.id())
                    .orElse(new ClubFile());
            } else {
                file = new ClubFile();
            }
            file.setLicenseUrl(fileDto.licenseUrl());
            file.setLogoUrl(fileDto.logoUrl());
            club.setFiles(file);
        }

        // Gestion des catégories (création si nouvelle)
        if (clubDto.categories() != null && !clubDto.categories().isEmpty()) {
            List<Category> categories = clubDto.categories().stream()
                .map(dto -> {
                    if (dto.categoryId() != null) {
                        return categoryRepository.findById(dto.categoryId())
                            .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.categoryId()));
                    } else {
                        Category cat = new Category();
                        cat.setName(dto.name());
                        cat.setDescription(dto.description());
                        cat.setAgeMin(dto.ageMin());
                        cat.setAgeMax(dto.ageMax());
                        return categoryRepository.save(cat);
                    }
                }).collect(Collectors.toList());
            club.setCategories(categories);
        } else {
            club.setCategories(new ArrayList<>());
        }

        // Gestion des membres (ignore membres sans ID)
        if (clubDto.members() != null && !clubDto.members().isEmpty()) {
            List<ClubMember> members = clubDto.members().stream()
                .filter(dto -> dto.userId() != null)
                .map(dto -> clubMemberRepository.findById(dto.userId())
                    .orElseThrow(() -> new EntityNotFoundException("Member not found: " + dto.userId())))
                .collect(Collectors.toList());
            club.setMembers(members);
        } else {
            club.setMembers(new ArrayList<>());
        }

        return club;
    }
}
