package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.User.UserFileDTO;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.model.UserFile;
import org.springframework.stereotype.Service;

@Service
public class UserFileMapper {

    public UserFileDTO toDto(UserFile file) {
        if (file == null) return null;

        return UserFileDTO.builder()
                .id(file.getId())
                .type(file.getType())
                .userId(file.getUser().getId())
                .build();
    }

    public UserFile toEntity(UserFileDTO dto, User user) {
        if (dto == null) return null;

        return UserFile.builder()
                .type(dto.getType())
                .user(user)
                .build();
    }
}
