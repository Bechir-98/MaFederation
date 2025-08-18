package com.MaFederation.MaFederation.controllers;
import com.MaFederation.MaFederation.dto.Player.PostPlayerDTO;
import com.MaFederation.MaFederation.dto.Player.ResponsePlayerDTO;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.services.PlayerService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.MaFederation.MaFederation.mappers.PlayerMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

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

 @PutMapping("/{id}")
    public ResponseEntity<ResponsePlayerDTO> updatePlayer(
            @PathVariable Integer id,
            @RequestBody PostPlayerDTO dto) {

        try {
            ResponsePlayerDTO updatedPlayer = playerService.updatePlayer(id, dto);
            return ResponseEntity.ok(updatedPlayer);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}


