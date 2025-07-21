package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.UserFileDTO;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.model.UserFile;
import org.springframework.stereotype.Service;

@Service
public class UserFileMapper {

    public UserFileDTO toDto(UserFile file) {
        if (file == null) return null;

        return UserFileDTO.builder()
                .id(file.getId())
                .fileUrl(file.getFileUrl())
                .type(file.getType())
                .userId(file.getUser().getUserId())
                .build();
    }

    public UserFile toEntity(UserFileDTO dto, User user) {
        if (dto == null) return null;

        return UserFile.builder()
                .id(dto.getId()) // optional: omit if you're letting JPA generate
                .fileUrl(dto.getFileUrl())
                .type(dto.getType())
                .user(user)
                .build();
    }
}
