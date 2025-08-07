package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Inherit equals/hashCode from Audit
public class User extends Audit {

    @Lob
    private byte[] profilePicture;

    // @Column(nullable = false, unique = true)
    private String email;


    private String passwordHash;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phoneNumber;

    private String address;

    // @Column(unique = true)
    private String nationalID;

    // @Column(name = "type", updatable = false)
    private String type;

    private String nationality;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFile> files;
}
