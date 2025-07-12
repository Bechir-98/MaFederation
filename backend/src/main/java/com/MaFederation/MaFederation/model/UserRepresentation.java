package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")  // avoid "user" as it can be reserved keyword in some DBs
@Data
@NoArgsConstructor
public class UserRepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    // You can use @ManyToOne for federation and club if you have entities for them
    @Column(nullable = false)
    private Integer federationID;

    @Column(nullable = false)
    private Integer clubID;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phoneNumber;

    private String address;

    @Column(nullable = false, unique = true)
    private String nationalID;

    private String nationality;

    private Integer profilePhotoFileID;

    private String userType;

    private Boolean isValidatedByFederation = false;

    private Integer validatedByUserID;

    private LocalDateTime validatedAt;

    @Lob
    private String rejectionReason;

  

    // Getters and setters (or use Lombok @Data/@Getter/@Setter to generate automatically)

    // Constructors, equals, hashCode, toString as needed
}
