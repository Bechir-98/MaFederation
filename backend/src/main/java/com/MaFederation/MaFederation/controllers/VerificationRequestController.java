package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.VerificationRequestResponseDTO.VerificationRequestResponseDTO;
import com.MaFederation.MaFederation.services.VerificationRequestService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/verification-requests")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class VerificationRequestController {

    private final VerificationRequestService service;

    // ✅ Get pending requests as DTOs
    @GetMapping("/pending")
    public ResponseEntity<List<VerificationRequestResponseDTO>> getPendingRequests() {
        return ResponseEntity.ok(service.getPendingRequests());
    }

    @PostMapping("/approve")
public ResponseEntity<VerificationRequestResponseDTO> approveRequest(
        @RequestBody ApproveRequestBody body) {
    return ResponseEntity.ok(service.approveRequest(body.getId(), body.getAdminName()));
}

@PostMapping("/reject")
public ResponseEntity<VerificationRequestResponseDTO> rejectRequest(
        @RequestBody RejectRequestBody body) {
    return ResponseEntity.ok(service.rejectRequest(body.getId(), body.getAdminName(), body.getReason()));
}

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
public static class VerificationRequestDTO {
    private Integer userId;
    private Integer clubId;
    }
}
