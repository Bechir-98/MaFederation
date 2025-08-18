package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.ClubVerificationRequestDTO;
import com.MaFederation.MaFederation.services.ClubVerificationRequestService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/club-verification-requests")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class ClubVerificationRequestController {

    private final ClubVerificationRequestService service;

    // ✅ Get pending club verification requests
    @GetMapping("/pending")
    public ResponseEntity<List<ClubVerificationRequestDTO>> getPendingRequests() {
        return ResponseEntity.ok(service.getPendingRequests());
    }

    // ✅ Approve a club verification request
    @PostMapping("/approve")
    public ResponseEntity<ClubVerificationRequestDTO> approveRequest(
            @RequestBody ApproveRequestBody body) {
        return ResponseEntity.ok(service.approveRequest(body.getId(), body.getAdminName()));
    }

    // ✅ Reject a club verification request
    @PostMapping("/reject")
    public ResponseEntity<ClubVerificationRequestDTO> rejectRequest(
            @RequestBody RejectRequestBody body) {
        return ResponseEntity.ok(service.rejectRequest(body.getId(), body.getAdminName(), body.getReason()));
    }

    // ✅ Create a new club verification request
    @PostMapping("/request")
    public ResponseEntity<ClubVerificationRequestDTO> createRequest(@RequestBody ClubRequestBody body) {
        return ResponseEntity.ok(service.createRequest(body.getClubId()));
    }

    // ---------------- Request Body DTOs ----------------

    @Data
    public static class ApproveRequestBody {
        private Integer id;
        private String adminName;
    }

    @Data
    public static class RejectRequestBody {
        private Integer id;
        private String adminName;
        private String reason;
    }

    @Data
    public static class ClubRequestBody {
        private Integer clubId;
    }
}
