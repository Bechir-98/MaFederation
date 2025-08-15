package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.controllers.VerificationRequestController.VerificationRequestDTO;
import com.MaFederation.MaFederation.dto.VerificationRequestResponseDTO.VerificationRequestResponseDTO;
import com.MaFederation.MaFederation.mappers.VerificationRequestMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.model.VerificationRequest;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.UserRepository;
import com.MaFederation.MaFederation.repository.VerificationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerificationRequestService {

    private final VerificationRequestRepository repo;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    // ✅ Get pending requests as DTOs
    public List<VerificationRequestResponseDTO> getPendingRequests() {
        return repo.findAll().stream()
                .map(VerificationRequestMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public VerificationRequestResponseDTO createRequestForUser(Integer userId, Integer clubId) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Fetch the club
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Club not found"));

        // Create verification request
        VerificationRequest req = VerificationRequest.builder()
                .user(user)
                .club(club)
                .validated(false)
                .build();

        VerificationRequest saved = repo.save(req);

        // Convert to DTO using mapper
        return VerificationRequestMapper.toDto(saved);
    }




    @Transactional
public VerificationRequestResponseDTO approveRequest(Integer requestId, String adminName) {
    VerificationRequest req = repo.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));

    // ✅ Update user validation info
    User user = req.getUser();
    user.setValidated(true);
    user.setValidatedBy(adminName);
    user.setValidationDate(java.time.LocalDateTime.now());
    // save user
    userRepository.save(user);

    // ✅ Delete the request from table
    repo.delete(req);

    // Return DTO for the deleted request (optional)
    return VerificationRequestMapper.toDto(req);
}

@Transactional
public VerificationRequestResponseDTO rejectRequest(Integer requestId, String adminName, String reason) {
    VerificationRequest req = repo.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));

    // ✅ Update request with rejection info
    req.setValidated(false);
    req.setValidatedBy(adminName);
    req.setValidationDate(java.time.LocalDateTime.now());
    req.setRejectionReason(reason);

    VerificationRequest saved = repo.save(req);

    return VerificationRequestMapper.toDto(saved);
}


  
}
