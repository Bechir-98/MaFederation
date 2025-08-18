package com.MaFederation.MaFederation.services;


import com.MaFederation.MaFederation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PlayerService playerService;
    private final StaffService staffService;
    private final AdministrationService administrationService;

   

    // ✅ Get user by ID and return the correct DTO
    public Object getUserDtoById(Integer userId) {
        // Try Player
        try {
            return playerService.getPlayerById(userId);
        } catch (RuntimeException ignored) {}

        // Try Staff
        try {
            return staffService.getStaffById(userId);
        } catch (RuntimeException ignored) {}

        // Try Administration
        try {
            return administrationService.getById(userId);
        } catch (RuntimeException ignored) {}

        throw new RuntimeException("User not found with ID: " + userId);
    }


    public void deleteUser(Integer userId) {
       this.userRepository.deleteById(userId);}
    }

