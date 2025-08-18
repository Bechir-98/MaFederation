package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.UserVerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VerificationRequestRepository extends JpaRepository<UserVerificationRequest, Integer> {
    List<UserVerificationRequest> findByValidatedFalse();
}
