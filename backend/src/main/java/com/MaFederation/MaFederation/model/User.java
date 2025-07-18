package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users") 
@Inheritance(strategy = InheritanceType.JOINED) 
// @DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING) 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

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
