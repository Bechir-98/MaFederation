package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.Player.PostPlayerDTO;
import com.MaFederation.MaFederation.mappers.PlayerMapper;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.repository.CategoryRepository;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final CategoryRepository categoryRepository;
    private final PlayerMapper playerMapper;




public Player createPlayer(PostPlayerDTO dto) {
    Player player = playerMapper.toEntity(dto);
    Club club = clubRepository.findById(dto.getClubId())
        .orElseThrow(() -> new RuntimeException("Club not found with id: " + dto.getClubId()));
    player.setClub(club);
    if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        player.setCategories(categories);
    }
    return playerRepository.save(player);
}


    // public List<Player> getAllPlayers() {
    //     return playerRepository.findAll();
    // }

    public Player getPlayerById(Integer id) {
        return playerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public Player updatePlayer(Integer id, PostPlayerDTO dto) {
        Player player = getPlayerById(id);
        // Update fields using dto (you can reuse `toEntity(dto)` and copy non-null values)
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setEmail(dto.getEmail());
        player.setPhoneNumber(dto.getPhoneNumber());
        player.setPosition(dto.getPosition());
        player.setJerseyNumber(dto.getJerseyNumber());
        player.setHeight(dto.getHeight());
        player.setWeight(dto.getWeight());
        player.setUpdatedAt(dto.getUpdatedAt());

        return playerRepository.save(player);
    }

    public void deletePlayer(Integer id) {
        playerRepository.deleteById(id);
    }
}
