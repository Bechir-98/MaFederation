package com.MaFederation.MaFederation.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.Club.PostClubDTO;
import com.MaFederation.MaFederation.dto.Club.ResponseClubDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubFile;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubMapper {

    private final CategoryRepository categoryRepository;

    // DTO ➜ Entity
    public Club fromDto(PostClubDTO dto) {
        if (dto == null) return null;

        Club club = new Club();
        club.setName(dto.getName());
        club.setLocation(dto.getLocation());
        club.setFoundedYear(dto.getFoundedYear());
        club.setContactEmail(dto.getContactEmail());
        club.setContactPhone(dto.getContactPhone());
        club.setBankAccount(dto.getBankAccount());
        club.setBankName(dto.getBankName());
        club.setLogo(dto.getLogo());

        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            if (categories.size() != dto.getCategoryIds().size()) {
                throw new EntityNotFoundException("One or more category IDs are invalid");
            }
            club.setCategories(categories);
        } else {
            club.setCategories(new ArrayList<>());
        }

        return club;
    }

    // Entity ➜ DTO
    public ResponseClubDTO toResponseDto(Club club) {
        if (club == null) return null;

        List<Integer> categoryIds = club.getCategories() == null ? new ArrayList<>() :
            club.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        List<Integer> memberIds = club.getMembers() == null ? new ArrayList<>() :
            club.getMembers().stream()
                .map(ClubMember::getId)
                .collect(Collectors.toList());

        List<Integer> fileIds = club.getFiles() == null ? new ArrayList<>() :
            club.getFiles().stream()
                .map(ClubFile::getId)
                .collect(Collectors.toList());

        return new ResponseClubDTO(
                club.getId(),
                club.getName(),
                club.getLocation(),
                club.getFoundedYear(),
                club.getContactEmail(),
                club.getContactPhone(),
                club.getBankAccount(),
                club.getBankName(),
                club.getLogo(),        
                categoryIds,         
                memberIds,
                fileIds,
                club.getCreatedAt(),   
                club.getUpdatedAt(),
                club.getCreatedBy(),
                club.getUpdatedBy(),
                club.isValidated(),
                club.getValidatedBy(),
                club.getValidationDate(),
                club.getRejectionReason()

                );

    }
}
