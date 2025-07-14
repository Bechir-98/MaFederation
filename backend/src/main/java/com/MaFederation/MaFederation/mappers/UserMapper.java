package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.UserDTO;
import com.MaFederation.MaFederation.model.UserRepresentation;
@Service
public class UserMapper {

    public UserDTO toDto(UserRepresentation user) {
        return new UserDTO(
            user.getUserID(),
            user.getClub() ,
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


        return user;
    }
}
