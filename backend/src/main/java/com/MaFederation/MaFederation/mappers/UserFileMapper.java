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
                .content(file.getContent())
                .type(file.getType())
                .userId(file.getUser().getUserId())
                .build();
    }

    public UserFile toEntity(UserFileDTO dto, User user) {
        if (dto == null) return null;

        return UserFile.builder()
                .content(dto.getContent())
                .type(dto.getType())
                .user(user)
                .build();
    }
}
