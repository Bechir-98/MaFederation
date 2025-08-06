package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.User.*;
import com.MaFederation.MaFederation.dto.Player.PostPlayerDTO;
import com.MaFederation.MaFederation.dto.Staff.PostStaffDTO;
import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
import com.MaFederation.MaFederation.mappers.*;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final PlayerMapper playerMapper;
    private final StaffMapper staffMapper;
    private final AdministrationMapper administrationMapper;

    @Transactional
    public String createUser(UserPostDTO dto) {
        if (dto == null) throw new IllegalArgumentException("User DTO cannot be null");

        User user;

        // Create appropriate entity based on type, casting DTO as needed
        switch (dto.getType()) {
            case "PLAYER":
                if (!(dto instanceof PostPlayerDTO))
                    throw new IllegalArgumentException("Expected PostPlayerDTO for type PLAYER");
                user = playerMapper.toEntity((PostPlayerDTO) dto);
                break;
            case "STAFF":
                if (!(dto instanceof PostStaffDTO))
                    throw new IllegalArgumentException("Expected PostStaffDTO for type STAFF");
                user = staffMapper.toEntity((PostStaffDTO) dto);
                break;
            case "ADMIN":
                if (!(dto instanceof PostAdminstrationDTO))
                    throw new IllegalArgumentException("Expected PostAdminstrationDTO for type ADMIN");
                user = administrationMapper.toEntity((PostAdminstrationDTO) dto);
                break;
            default:
                user = userMapper.toEntity(dto);
        }

        userRepository.save(user);
        return "User created successfully";
    }

    public ResponseUserDTO loadUser(Integer userId) {
        return userRepository.findById(userId).map(user -> {
            if (user instanceof com.MaFederation.MaFederation.model.Player player) {
                return playerMapper.toDto(player);
            } else if (user instanceof com.MaFederation.MaFederation.model.Staff staff) {
                return staffMapper.toDto(staff);
            } else if (user instanceof com.MaFederation.MaFederation.model.Administration admin) {
                return administrationMapper.toDto(admin);
            } else {
                return userMapper.toResponseDto(user);
            }
        }).orElse(null);
    }
}
