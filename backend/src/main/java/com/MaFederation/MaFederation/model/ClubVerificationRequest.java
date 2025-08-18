
package com.MaFederation.MaFederation.model;
import com.MaFederation.MaFederation.enums.ClubMemberType;
import com.MaFederation.MaFederation.enums.VerificationTargetType;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "club_verification_requests") 
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClubVerificationRequest extends Audit {

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false) // usually a request must belong to a club
    private Club club;
}
