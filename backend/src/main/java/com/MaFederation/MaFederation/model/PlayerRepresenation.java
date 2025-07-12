package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "player",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_jersey_per_club_season",
        columnNames = {"userID", "jerseyNumber"}
    )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerRepresenation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Foreign key column
    @Column(name = "userID", nullable = false)
    private Integer userID;

    // JPA ManyToOne relationship to User entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private UserRepresentation user;

    private String position;

    @Column(name = "jerseyNumber", nullable = false)
    private Integer jerseyNumber;

    @Column(precision = 5, scale = 2)
    private BigDecimal height;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;



    // Optional: add PrePersist and PreUpdate hooks for timestamps

    

}
