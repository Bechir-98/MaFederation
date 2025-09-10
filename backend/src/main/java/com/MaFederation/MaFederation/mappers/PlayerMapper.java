package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.Player.PostPlayerDTO;
import com.MaFederation.MaFederation.dto.Player.ResponsePlayerDTO;
import com.MaFederation.MaFederation.enums.ClubMemberType;
import com.MaFederation.MaFederation.enums.ValidationStatus;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Player;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlayerMapper {

    // Map Player entity to ResponsePlayerDTO
    public ResponsePlayerDTO toDto(Player player) {
        if (player == null) return null;

        ResponsePlayerDTO dto = new ResponsePlayerDTO();

        dto.setId(player.getId());
        dto.setEmail(player.getEmail());
        dto.setFirstName(player.getFirstName());
        dto.setLastName(player.getLastName());
        dto.setDateOfBirth(player.getDateOfBirth());
        dto.setGender(player.getGender());
        dto.setPhoneNumber(player.getPhoneNumber());
        dto.setAddress(player.getAddress());
        dto.setNationalID(player.getNationalID());
        dto.setNationality(player.getNationality());

        if (player.getClub() != null) {
            dto.setClubId(player.getClub().getId());
        }

        dto.setProfilePicture(player.getProfilePicture());
        dto.setPosition(player.getPosition());
        dto.setJerseyNumber(player.getJerseyNumber());
        dto.setHeight(player.getHeight());
        dto.setWeight(player.getWeight());

        if (player.getCategories() != null) {
            dto.setCategories(
                player.getCategories()
                      .stream()
                      .map(Category::getName)
                      .collect(Collectors.toList())
            );
        }
        dto.setCreatedAt(player.getCreatedAt());
        dto.setUpdatedAt(player.getUpdatedAt());
        dto.setCreatedBy(player.getCreatedBy());
        dto.setUpdatedBy(player.getUpdatedBy());
        dto.setValidated(player.getValidated());
        dto.setValidatedBy(player.getValidatedBy());
        dto.setValidationDate(player.getValidationDate());
        

        return dto;
    }

    // Map PostPlayerDTO to Player entity (without setting relations)
    public Player toEntity(PostPlayerDTO dto) {
        if (dto == null) return null;

        Player player = new Player();

        player.setEmail(dto.getEmail());
        player.setPassword(dto.getPasswordHash());
        player.setProfilePicture(dto.getProfilePicture());
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setDateOfBirth(dto.getDateOfBirth());
        player.setGender(dto.getGender());
        player.setPhoneNumber(dto.getPhoneNumber());
        player.setAddress(dto.getAddress());
        player.setNationalID(dto.getNationalID());
        player.setNationality(dto.getNationality());
        player.setType(ClubMemberType.PLAYER);
        player.setValidated(ValidationStatus.nonValidated);
        player.setValidatedBy(dto.getValidatedBy());
        player.setPosition(dto.getPosition());
        player.setJerseyNumber(dto.getJerseyNumber());
        player.setHeight(dto.getHeight());
        player.setWeight(dto.getWeight());


        return player;
    }
}
