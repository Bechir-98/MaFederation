
package com.MaFederation.MaFederation.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "verification_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserVerificationRequest extends Audit {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user; 
    
    private String targetType;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = true)
    private Club club; 

 
}
