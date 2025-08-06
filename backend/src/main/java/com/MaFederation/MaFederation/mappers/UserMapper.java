package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.model.UserFile;
import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
import com.MaFederation.MaFederation.dto.User.UserPostDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public ResponseUserDTO toResponseDto(User user) {
        if (user == null) return null;

        ResponseUserDTO dto = new ResponseUserDTO();
        dto.setId(user.getId());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setGender(user.getGender());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setNationalID(user.getNationalID());
        dto.setNationality(user.getNationality());
        dto.setType(user.getType());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setCreatedBy(user.getCreatedBy());
        dto.setUpdatedBy(user.getUpdatedBy());

        List<Integer> fileIds = user.getFiles() != null
            ? user.getFiles().stream()
                .map(UserFile::getId)
                .collect(Collectors.toList())
            : null;

        dto.setFileIds(fileIds);

        return dto;
    }

    public User toEntity(UserPostDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setProfilePicture(dto.getProfilePicture());
        user.setPasswordHash(dto.getPasswordHash());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setNationalID(dto.getNationalID());
        user.setNationality(dto.getNationality());
        user.setType(dto.getType());

        return user;
    }
}
