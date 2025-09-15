package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.mod.ModDTO;
import com.MaFederation.MaFederation.mappers.UserMapper;
import com.MaFederation.MaFederation.model.Moderator;
import com.MaFederation.MaFederation.repository.ModeratorRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ModService {

    private final ModeratorRepository moderatorRepository;
    private final UserMapper userMapper;

    // Get all moderators
    public List<ModDTO> getAllModerators() {
        return moderatorRepository.findAll().stream()
                .map(userMapper::toModDto)
                .collect(Collectors.toList());
    }

    // Get moderator by ID
    public Optional<ModDTO> getModeratorById(Integer moderatorId) {
        return moderatorRepository.findById(moderatorId)
                .map(userMapper::toModDto);
    }

    // Create a new moderator
    @Transactional
    public ModDTO createModerator(ModDTO modDTO) {
        if (modDTO == null) {
            throw new IllegalArgumentException("Moderator data cannot be null");
        }

        Moderator moderator = Moderator.builder()
                .firstName(modDTO.getFirstName())
                .lastName(modDTO.getLastName())
                .email(modDTO.getEmail())
                .dateOfBirth(modDTO.getDateOfBirth() != null ? modDTO.getDateOfBirth() : null)
                .gender(modDTO.getGender())
                .phoneNumber(modDTO.getPhoneNumber())
                .address(modDTO.getAddress())
                .nationality(modDTO.getNationality())
                .nationalID(modDTO.getNationalID())
                .role(modDTO.getRole())
                .build();

        Moderator saved = moderatorRepository.save(moderator);
        return userMapper.toModDto(saved);
    }

    // Select a moderator in session
    public boolean selectModeratorInSession(HttpSession session, Integer moderatorId, Set<String> allowedRoles) {
        Moderator mod = moderatorRepository.findById(moderatorId).orElse(null);
        if (mod == null) return false;

//        if (allowedRoles != null && !allowedRoles.isEmpty()) {
//            boolean hasRole = mod.getRole().stream()
//                    .anyMatch(r -> allowedRoles.contains(r.getName()));
//            if (!hasRole) return false;}


        session.setAttribute("selectedModeratorId", moderatorId);
        return true;
    }

    // Get selected moderator from session
    public Optional<ModDTO> getSelectedModerator(HttpSession session) {
        Object obj = session.getAttribute("selectedModeratorId");
        if (obj == null) return Optional.empty();

        Integer moderatorId;
        if (obj instanceof Integer) {
            moderatorId = (Integer) obj;
        } else {
            return Optional.empty();
        }

        return getModeratorById(moderatorId);
    }

    // Delete moderator
    @Transactional
    public void deleteModerator(Integer moderatorId) {
        if (!moderatorRepository.existsById(moderatorId)) {
            throw new IllegalArgumentException("Moderator not found");
        }
        moderatorRepository.deleteById(moderatorId);
    }
}
