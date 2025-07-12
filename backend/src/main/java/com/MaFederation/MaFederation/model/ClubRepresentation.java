package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "club_representation") // or just "club" if that's the table name
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubRepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clubID;

    // Foreign key to Federation
    private Integer federationID;

    private String name;
    private String location;
    private Integer foundedYear;

    private String contactEmail;
    private String contactPhone;

    private String bankAccount;
    private String bankName;

    // Foreign key to File table
    private Integer logoFileID;


    private Boolean isValidatedByFederation = false;
    private LocalDateTime validatedAt;

    // Foreign key to User table
    private Integer validatedByUserID;

    // @Lob
    private String rejectionReason;


}
