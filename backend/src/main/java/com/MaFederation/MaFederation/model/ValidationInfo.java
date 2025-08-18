package com.MaFederation.MaFederation.model;

import java.time.LocalDateTime;

import com.MaFederation.MaFederation.enums.ValidationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ValidationInfo {
    

    @Enumerated(EnumType.STRING)
    private ValidationStatus validated;
    private String validatedBy;
    private LocalDateTime validationDate;
    private String rejectionReason;

}
    