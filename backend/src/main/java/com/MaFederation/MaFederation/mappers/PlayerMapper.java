package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.ClubMemberDTO;
import com.MaFederation.MaFederation.dto.PlayerDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerMapper {

    private final ClubMemberMapper clubMemberMapper;

    public PlayerMapper(ClubMemberMapper clubMemberMapper) {
        this.clubMemberMapper = clubMemberMapper;
    }

    public PlayerDTO toDto(Player player) {
        if (player == null) return null;

        PlayerDTO dto = new PlayerDTO();

        // Reuse ClubMemberMapper (which uses UserMapper internally)
        ClubMemberDTO baseDto = clubMemberMapper.toDto(player);
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
        dto.setFileIds(baseDto.getFileIds());  // files here
        dto.setRole(baseDto.getRole());
        dto.setClubId(baseDto.getClubId());

        // Player-specific
        dto.setPosition(player.getPosition()); // player.getPosition() returns Position enum
        dto.setJerseyNumber(player.getJerseyNumber());
        dto.setHeight(player.getHeight());
        dto.setWeight(player.getWeight());

        if (player.getCategories() != null) {
            dto.setCategoryIds(player.getCategories().stream()
                    .map(c -> c.getCategoryId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Player toEntity(PlayerDTO dto, List<Category> categories) {
        if (dto == null) return null;

        // Use ClubMemberMapper to get base entity (which calls UserMapper)
        ClubMember base = clubMemberMapper.toEntity(dto);

        Player player = new Player();

        // Copy inherited fields from base ClubMember
        player.setUserId(base.getUserId());
        player.setEmail(base.getEmail());
        player.setFirstName(base.getFirstName());
        player.setLastName(base.getLastName());
        player.setDateOfBirth(base.getDateOfBirth());
        player.setGender(base.getGender());
        player.setPhoneNumber(base.getPhoneNumber());
        player.setAddress(base.getAddress());
        player.setNationalID(base.getNationalID());
        player.setNationality(base.getNationality());
        player.setFiles(base.getFiles());
        player.setRole(base.getRole());
        player.setClub(base.getClub());

        // Player-specific
        player.setPosition(dto.getPosition());
        player.setJerseyNumber(dto.getJerseyNumber());
        player.setHeight(dto.getHeight());
        player.setWeight(dto.getWeight());
        player.setCategories(categories); // categories loaded externally

        return player;
    }
}

