package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;
import com.MaFederation.MaFederation.dto.Player.PostPlayerDTO;
import com.MaFederation.MaFederation.dto.Player.ResponcePlayerDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.services.CategoryService;
import com.MaFederation.MaFederation.services.ClubServices;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PlayerMapper {

    private final ClubMemberMapper clubMemberMapper;
    private final ClubServices clubServices;
    private final CategoryService categoryService;

    public PlayerMapper(ClubMemberMapper clubMemberMapper, ClubServices clubServices, CategoryService categoryService) {
        this.clubMemberMapper = clubMemberMapper;
        this.clubServices = clubServices;
        this.categoryService = categoryService;
    }

    // Convert from Player entity to ResponcePlayerDTO
    public ResponcePlayerDTO toDto(Player player) {
        if (player == null) return null;

        // Reuse shared ClubMember mapping
        ResponceClubMemberDTO baseDto = clubMemberMapper.toResponseDto(player);

        ResponcePlayerDTO dto = new ResponcePlayerDTO();
        dto.setUserId(baseDto.getUserId());
        dto.setEmail(baseDto.getEmail());
        dto.setFirstName(baseDto.getFirstName());
        dto.setLastName(baseDto.getLastName());
        dto.setDateOfBirth(baseDto.getDateOfBirth());
        dto.setGender(baseDto.getGender());
        dto.setPhoneNumber(baseDto.getPhoneNumber());
        dto.setAddress(baseDto.getAddress());
        dto.setNationalID(baseDto.getNationalID());
        dto.setNationality(baseDto.getNationality());
        dto.setClubId(baseDto.getClubId());

        // Player-specific fields
        dto.setPosition(player.getPosition());
        dto.setJerseyNumber(player.getJerseyNumber());
        dto.setHeight(player.getHeight());
        dto.setWeight(player.getWeight());

        if (player.getCategories() != null) {
            dto.setCategoryIds(
                player.getCategories().stream()
                      .map(Category::getCategoryId)
                      .collect(Collectors.toList())
            );
        }

        return dto;
    }

    // Convert from PostPlayerDTO to Player entity
    public Player toEntity(PostPlayerDTO dto) {
        if (dto == null) return null;

        Player player = new Player();

        // Common ClubMember fields
        player.setUserId(dto.getUserId());
        player.setEmail(dto.getEmail());
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setDateOfBirth(dto.getDateOfBirth());
        player.setGender(dto.getGender());
        player.setPhoneNumber(dto.getPhoneNumber());
        player.setAddress(dto.getAddress());
        player.setNationalID(dto.getNationalID());
        player.setNationality(dto.getNationality());
        player.setType("PLAYER");
        player.setPasswordHash(dto.getPasswordHash());

        // Club (if present)
        if (dto.getClubId() != null) {
            player.setClub(clubServices.getClub(dto.getClubId()));
        }

        // Categories (if present)
        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            player.setCategories(categoryService.getCategoriesByIdsEntity(dto.getCategoryIds()));
        }

        // Player-specific fields
        player.setPosition(dto.getPosition());
        player.setJerseyNumber(dto.getJerseyNumber());
        player.setHeight(dto.getHeight());
        player.setWeight(dto.getWeight());

        return player;
    }
}
