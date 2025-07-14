package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @ManyToOne
    private ClubRepresentation club; // ✅ Et pas Integer


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

    private String userType; // E.g. "player", "staff", "admin"

}
