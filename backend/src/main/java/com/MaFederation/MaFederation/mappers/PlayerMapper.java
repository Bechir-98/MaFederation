package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.PlayerDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerMapper {

    public PlayerDTO toDto(Player player) {
        if (player == null) {
            return null;
        }

        List<Integer> categoryIds = null;
        if (player.getCategories() != null) {
            categoryIds = player.getCategories()
                .stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toList());
        }

        return new PlayerDTO(
            player.getUserId(),
            player.getPosition(),
            player.getJerseyNumber(),
            player.getHeight(),
            player.getWeight(),
            categoryIds
        );
    }

    public Player toEntity(PlayerDTO dto, List<Category> categories) {
        if (dto == null) {
            return null;
        }

        Player player = new Player();
        player.setUserId(dto.userId()); // si setter existe et gestion possible, sinon gérer l'id autrement
        player.setPosition(dto.position());
        player.setJerseyNumber(dto.jerseyNumber());
        player.setHeight(dto.height());
        player.setWeight(dto.weight());
        player.setCategories(categories); // categories doivent être récupérées en amont via repo

        return player;
    }
}
