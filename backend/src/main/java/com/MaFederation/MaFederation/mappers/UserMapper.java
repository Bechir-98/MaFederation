package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;
import com.MaFederation.MaFederation.dto.UserDTO;
import com.MaFederation.MaFederation.model.UserRepresentation;
import com.MaFederation.MaFederation.model.ClubRepresentation;

import java.time.LocalDateTime;

@Service
public class UserMapper {

    public UserDTO toDto(UserRepresentation user) {
        return new UserDTO(
            user.getUserID(),
            user.getClub(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getDateOfBirth(),
            user.getGender(),
            user.getPhoneNumber(),
            user.getAddress(),
            user.getNationalID(),
            user.getNationality(),
            user.getUserType()
        );
    }

    public UserRepresentation fromDto(UserDTO dto) {
        UserRepresentation user = new UserRepresentation();

        user.setUserID(dto.userID());
        
        // Club should be fetched elsewhere (e.g., via service or JPA repository)
        // so we just leave it as null for now or handle it outside the mapper
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setDateOfBirth(dto.dateOfBirth());
        user.setGender(dto.gender());
        user.setPhoneNumber(dto.phoneNumber());
        user.setAddress(dto.address());
        user.setNationalID(dto.nationalID());
        user.setNationality(dto.nationality());
        user.setUserType(dto.userType());
        user.setLastLogin(dto.lastLogin());

        // PhotoFileID should also be set via service if needed
        return user;
    }
}
