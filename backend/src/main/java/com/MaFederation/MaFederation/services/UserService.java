package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.model.Player;
import com.MaFederation.MaFederation.model.Staff;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.repository.UserRepository;
import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PlayerService playerService;
    private final StaffService staffService;
    private final AdministrationService administrationService;
    private  final LogsService logsService;
    private final AuthUtils authUtils ;

    public Object getUserDtoById(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Check ClubMemberType if available
            if (user instanceof Player ) {
                return playerService.getPlayerById(userId);
            }
            if (user instanceof Staff) {
                return staffService.getStaffById(userId);
            }
            if (user instanceof Administration) {
                return administrationService.getById(userId);
            }

            // Regular user (club admin, etc.)
            ResponseUserDTO dto = new ResponseUserDTO();
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            return dto;
        }

        throw new RuntimeException("User not found with ID: " + userId);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        String admin= authUtils.getCurrentUserId();
        logsService.log("user "+ userId +" was deleted ", admin);
    }
}
