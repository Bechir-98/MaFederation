package com.MaFederation.MaFederation.mappers;
import org.springframework.stereotype.Service;
import com.MaFederation.MaFederation.dto.PlayerDto;
import com.MaFederation.MaFederation.model.PlayerRepresenation;


@Service
public class PlayerMapper {

    public PlayerDto todto(PlayerRepresenation player) {
        if (player == null) {
            return null;
        }
        
        return new PlayerDto(
            player.getPlayerId(),
            player.getPosition(),
            player.getJerseyNumber(),
            player.getHeight(),
            player.getWeight(),
            player.getUser()
        );
    }

    public PlayerRepresenation toEntity(PlayerDto dto) {
        if (dto == null) {
            return null;
        }

        PlayerRepresenation player = new PlayerRepresenation();

        player.setPlayerId(dto.id());
        player.setPosition(dto.position());
        player.setJerseyNumber(dto.jerseynumber());
        player.setHeight(dto.height());
        player.setWeight(dto.weight());

        // Set foreign key userID only
        player.setUser(dto.user());

        return player;
    }
}
