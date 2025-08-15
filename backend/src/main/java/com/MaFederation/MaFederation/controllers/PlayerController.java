package com.MaFederation.MaFederation.controllers;
import com.MaFederation.MaFederation.dto.Player.PostPlayerDTO;
import com.MaFederation.MaFederation.dto.Player.ResponsePlayerDTO;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.services.PlayerService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.MaFederation.MaFederation.mappers.PlayerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/players")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;


@PostMapping("/addplayer")
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

 @PostMapping("/select")
public ResponseEntity<Void> selectPlayer(@RequestBody Map<String, Integer> body, HttpSession session) {
    Integer playerId = body.get("playerId");
    if (playerId == null) {
        return ResponseEntity.badRequest().build();
    }
    session.setAttribute("selectedPlayerId", playerId);
    return ResponseEntity.ok().build();
}


    // GET endpoint to get the selected player (no ID in URL)
    @GetMapping("/profile")
    public ResponseEntity<ResponsePlayerDTO> getSelectedPlayer(HttpSession session) {
        Integer playerId = (Integer) session.getAttribute("selectedPlayerId");
        if (playerId == null) {
            return ResponseEntity.badRequest().build();
        }
        Player player = playerService.getPlayerById(playerId);
        return ResponseEntity.ok(playerMapper.toDto(player));
    }
}


