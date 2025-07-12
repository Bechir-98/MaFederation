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

        var user = player.getUser();

        return new PlayerDto(
            player.getId(),
            player.getPosition(),
            player.getJerseyNumber(),
            player.getHeight(),
            player.getWeight(),

            user != null ? user.getUserID() : null,
            user != null ? user.getFirstName() : null,
            user != null ? user.getLastName() : null,
            user != null ? user.getEmail() : null,
            user != null ? user.getPhoneNumber() : null,
            user != null ? user.getGender() : null,
            user != null ? user.getNationality() : null,
            user != null ? user.getAddress() : null
        );
    }

    public PlayerRepresenation toEntity(PlayerDto dto) {
        if (dto == null) {
            return null;
        }

        PlayerRepresenation player = new PlayerRepresenation();

        player.setId(dto.id());
        player.setPosition(dto.position());
        player.setJerseyNumber(dto.jerseynumber());
        player.setHeight(dto.height());
        player.setWeight(dto.weight());

        // Set foreign key userID only
        player.setUserID(dto.userID());

        return player;
    }
}
