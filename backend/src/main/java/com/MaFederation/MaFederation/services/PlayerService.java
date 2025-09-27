package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.Player.PostPlayerDTO;
import com.MaFederation.MaFederation.dto.Player.ResponsePlayerDTO;
import com.MaFederation.MaFederation.mappers.PlayerMapper;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.repository.CategoryRepository;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final CategoryRepository categoryRepository;
    private final PlayerMapper playerMapper;
    private  final LogsService logsService;
    private final AuthUtils authUtils ;


public Player createPlayer(PostPlayerDTO dto) {
    Player player = playerMapper.toEntity(dto);

    Club club = clubRepository.findById(dto.getClubId())
        .orElseThrow(() -> new RuntimeException("Club not found with id: " + dto.getClubId()));
    player.setClub(club);

    // Auto-select categories based on player age if categoryIds not explicitly provided
    if ((dto.getCategoryIds() == null || dto.getCategoryIds().isEmpty()) && player.getDateOfBirth() != null) {
        int playerAge = Period.between(player.getDateOfBirth(), LocalDate.now()).getYears();

        List<Category> matchedCategories = categoryRepository.findByAgeMinLessThanEqualAndAgeMaxGreaterThanEqual(playerAge, playerAge);

        player.setCategories(matchedCategories);
    } else if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        player.setCategories(categories);   
    }
    String admin= authUtils.getCurrentUserId();
    logsService.log("Club " + club.getName()+ " created player "+ player.getId(), admin);

    return playerRepository.save(player);
}


    public ResponsePlayerDTO getPlayerById(Integer id) {
    Player player = playerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Player not found"));
    return playerMapper.toDto(player);
}


    public ResponsePlayerDTO updatePlayer(Integer id, PostPlayerDTO dto) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));;
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
        Player update= playerRepository.save(player);
        String admin= authUtils.getCurrentUserId();
        logsService.log("player "+ player.getId() +"was updated", admin);
        return playerMapper.toDto(update);
    }
}
