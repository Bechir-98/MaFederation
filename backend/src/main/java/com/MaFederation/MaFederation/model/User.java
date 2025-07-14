package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users") 
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // stratégie d’héritage unique
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING) // pour différencier les sous-classes
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

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

    @Column(unique = true)
    private String nationalID;

    private String nationality;


}
