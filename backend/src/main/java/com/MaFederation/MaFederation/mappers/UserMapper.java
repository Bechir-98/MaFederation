package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;
import com.MaFederation.MaFederation.dto.UserDTO;
import com.MaFederation.MaFederation.model.User;

@Service
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
            user.getUserID(),
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

    public User fromDto(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();

        user.setUserID(dto.userId());
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
