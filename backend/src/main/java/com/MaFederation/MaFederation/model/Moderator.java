package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import com.MaFederation.MaFederation.enums.RoleName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "moderators")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false) // Don’t include Audit.id
public class Moderator extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    private String email;
    private String passwordHash;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;

    private String nationality;
    private String nationalID;

   

    @Enumerated(EnumType.STRING)
    private RoleName role;

    private boolean active = true;
}
