package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.VerificationRequestResponseDTO.VerificationRequestResponseDTO;
import com.MaFederation.MaFederation.enums.ValidationStatus;
import com.MaFederation.MaFederation.mappers.VerificationRequestMapper;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.model.ClubMember;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.model.UserVerificationRequest;
import com.MaFederation.MaFederation.repository.ClubMemberRepository;
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
public class UserVerificationRequestService {

    private final VerificationRequestRepository repo;
    private final ClubMemberRepository clubMemberRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private  final LogsService logsService;
    private final AuthUtils authUtils ;

    // ✅ Get pending requests as DTOs
    public List<VerificationRequestResponseDTO> getPendingRequests() {
        return repo.findAll().stream()
                .map(VerificationRequestMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public VerificationRequestResponseDTO createRequestForUser(Integer userId, Integer clubId) {
        // Fetch the user
        ClubMember user = clubMemberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));




        user.setValidated(ValidationStatus.pending);
        // Fetch the club
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Club not found"));
        UserVerificationRequest req = UserVerificationRequest.builder()
                .user(user)
                .club(club)
                .targetType(user.getType().name())
                .build();
        UserVerificationRequest saved = repo.save(req);
        String admin= authUtils.getCurrentUserId();
        logsService.log("club" + club.getName() + "requested validation for user "+  user.getId(), admin);
        return VerificationRequestMapper.toDto(saved);
    }




    @Transactional
public VerificationRequestResponseDTO approveRequest(Integer requestId, String adminName) {
    UserVerificationRequest req = repo.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));

    User user = req.getUser();
    user.setValidated(ValidationStatus.validated);
    user.setValidationDate(java.time.LocalDateTime.now());
    user.setRejectionReason(null);
        String admin= authUtils.getCurrentUserId();
        logsService.log("Approved User" + user.getId(), admin);
        user.setValidatedBy(admin);
    // save user
        userRepository.save(user);


    repo.delete(req);

    // Return DTO for the deleted request (optional)
    return VerificationRequestMapper.toDto(req);
}

@Transactional
public VerificationRequestResponseDTO rejectRequest(Integer requestId, String adminName, String reason) {
    UserVerificationRequest req = repo.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));

      User user = req.getUser();          
    // ✅ Update request with rejection info
    user.setValidated(ValidationStatus.rejected);

    user.setValidationDate(java.time.LocalDateTime.now());
    user.setRejectionReason(reason);
    String admin= authUtils.getCurrentUserId();
    logsService.log("Rejected User" + user.getId(), admin);
    user.setValidatedBy(admin);
    userRepository.save(user);
    repo.delete(req);
    return VerificationRequestMapper.toDto(req);
}


  
}
