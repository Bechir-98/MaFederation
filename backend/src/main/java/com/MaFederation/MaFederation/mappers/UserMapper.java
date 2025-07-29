package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
import com.MaFederation.MaFederation.dto.User.UserPostDTO;
import com.MaFederation.MaFederation.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public ResponseUserDTO toResponseDto(User user) {
        if (user == null) return null;

        return new ResponseUserDTO(
            user.getUserId(),
            user.getProfilePicture(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getDateOfBirth(),
            user.getGender(),
            user.getPhoneNumber(),
            user.getAddress(),
            user.getNationalID(),
            user.getNationality()
        );
    }

    public User toEntity(UserPostDTO dto) {
        if (dto == null) return null;

        return User.builder()
                .profilePicture(dto.getProfilePicture())
                .passwordHash(dto.getPasswordHash())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .nationalID(dto.getNationalID())
                .nationality(dto.getNationality())
                .build();
    }
}