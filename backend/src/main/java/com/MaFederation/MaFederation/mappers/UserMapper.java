package com.MaFederation.MaFederation.mappers;

import com.MaFederation.MaFederation.dto.UserDTO;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.model.UserFile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        // Map file IDs from associated UserFile objects
        List<Integer> fileIds = null;
        if (user.getFiles() != null) {
            fileIds = user.getFiles()
                    .stream()
                    .map(UserFile::getId)
                    .collect(Collectors.toList());
        }

        return new UserDTO(
            user.getUserId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getDateOfBirth(),
            user.getGender(),
            user.getPhoneNumber(),
            user.getAddress(),
            user.getNationalID(),
            user.getNationality(),
            fileIds // ✅ added file IDs
        );
    }

    public User fromDto(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUserId(dto.getUserId());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setNationalID(dto.getNationalID());
        user.setNationality(dto.getNationality());

        // ❌ Don't map files from fileIds in DTO directly — should be handled at the service level
        // (e.g. fetch UserFile by ID and set the list in the service layer if needed)

        return user;
    }
}
