package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Integer> {
    List<VerificationRequest> findByValidatedFalse();
}
