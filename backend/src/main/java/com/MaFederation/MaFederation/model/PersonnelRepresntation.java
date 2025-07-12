package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "personnel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PersonnelRepresntation {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personnelID;

    // Foreign key to User
    @Column(nullable = false)
    private Integer userID;

    private String certification;

    private String personnelType;

    private String role;

    @Lob
    private String remarks;

    // Foreign key to File (nomination letter)
    private Integer nominationLetterFileID;

  
}
