package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

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
    // @Column(nullable = false)
    // private String passwordHash;

    @Lob
    private byte[] profilePicture;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phoneNumber;

    private String address;

    @Column(unique = true)
    private String nationalID;

    private String nationality;


   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<UserFile> files;

}
