package com.MaFederation.MaFederation.model;

import java.time.LocalDateTime;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class ValidationInfo {
    private boolean validated;
    private String validatedBy;
    private LocalDateTime validationDate;
    private String rejectionReason;

}
