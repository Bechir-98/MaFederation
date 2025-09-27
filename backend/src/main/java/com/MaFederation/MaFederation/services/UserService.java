package com.MaFederation.MaFederation.services;

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
        // 1️⃣ Try User repository (includes club admins)
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            ResponseUserDTO dto = new ResponseUserDTO();
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            return dto;
        }

        // 2️⃣ Try Player
        try { return playerService.getPlayerById(userId); } catch (RuntimeException ignored) {}

        // 3️⃣ Try Staff
        try { return staffService.getStaffById(userId); } catch (RuntimeException ignored) {}

        // 4️⃣ Try Administration
        try { return administrationService.getById(userId); } catch (RuntimeException ignored) {}

        throw new RuntimeException("User not found with ID: " + userId);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        String admin= authUtils.getCurrentUserId();
        logsService.log("user "+ userId +" was deleted ", admin);
    }
}
