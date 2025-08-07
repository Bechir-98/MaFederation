package com.MaFederation.MaFederation.controllers;
import com.MaFederation.MaFederation.dto.Player.PostPlayerDTO;
import com.MaFederation.MaFederation.dto.Player.ResponsePlayerDTO;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.services.PlayerService;
import lombok.RequiredArgsConstructor;
import com.MaFederation.MaFederation.mappers.PlayerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;


@PostMapping()
public ResponsePlayerDTO createPlayer(
    @RequestPart("player") PostPlayerDTO dto,
    @RequestPart(value = "profilePicture", required = false) MultipartFile profilePictureFile
) throws IOException {

    if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
        dto.setProfilePicture(profilePictureFile.getBytes());
    }

    Player player = playerService.createPlayer(dto);
    return playerMapper.toDto(player);
}   


    @GetMapping
    public ResponseEntity<List<ResponsePlayerDTO>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        List<ResponsePlayerDTO> dtos = players.stream()
                                              .map(playerMapper::toDto)
                                              .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePlayerDTO> getPlayerById(@PathVariable Integer id) {
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(playerMapper.toDto(player));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePlayerDTO> updatePlayer(@PathVariable Integer id, @RequestBody PostPlayerDTO dto) {
        Player updated = playerService.updatePlayer(id, dto);
        return ResponseEntity.ok(playerMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Integer id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
