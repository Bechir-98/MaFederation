package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

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
    private Integer playerId;


   @ManyToOne // ou @OneToOne selon le lien logique
@JoinColumn(name = "user_id")
private UserRepresentation user; // ✅ Entité liée correctement


    private String position;

    @Column(name = "jerseyNumber", nullable = false)
    private Integer jerseyNumber;

    @Column(precision = 5, scale = 2)
    private BigDecimal height;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;



    // Optional: add PrePersist and PreUpdate hooks for timestamps

    

}
