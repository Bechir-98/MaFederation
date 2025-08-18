package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.ClubVerificationRequestDTO;
import com.MaFederation.MaFederation.enums.ValidationStatus;
import com.MaFederation.MaFederation.mappers.ClubVerificationRequestMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubVerificationRequest;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.ClubVerificationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubVerificationRequestService {

    private final ClubVerificationRequestRepository repo;
    private final ClubRepository clubRepository;

    // ✅ Get all pending club verification requests
    public List<ClubVerificationRequestDTO> getPendingRequests() {
        return repo.findAll().stream()
                .map(ClubVerificationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Create a new club verification request
    @Transactional
    public ClubVerificationRequestDTO createRequest(Integer clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Club not found"));

            club.setValidated(ValidationStatus.pending);
        ClubVerificationRequest req = ClubVerificationRequest.builder()
                .club(club)
                .validated(ValidationStatus.pending)
                .build();

        ClubVerificationRequest saved = repo.save(req);
        return ClubVerificationRequestMapper.toDTO(saved);
    }

    // ✅ Approve club verification
    @Transactional
    public ClubVerificationRequestDTO approveRequest(Integer requestId, String adminName) {
        ClubVerificationRequest req = repo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        // Update club validation fields
        Club club = req.getClub();
        club.setValidated(ValidationStatus.validated);
        club.setValidatedBy(adminName);
        club.setValidationDate(LocalDateTime.now());
        club.setRejectionReason(null);
        clubRepository.save(club);

        repo.delete(req); // remove request after approval
        return ClubVerificationRequestMapper.toDTO(req);
    }

    // ✅ Reject club verification
    @Transactional
    public ClubVerificationRequestDTO rejectRequest(Integer requestId, String adminName, String reason) {
        ClubVerificationRequest req = repo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        Club club = req.getClub();
        club.setValidated(ValidationStatus.rejected);
        club.setValidatedBy(adminName);
        club.setValidationDate(LocalDateTime.now());
        club.setRejectionReason(reason);
        clubRepository.save(club);
        repo.delete(req);
        return ClubVerificationRequestMapper.toDTO(req);
    }
}
