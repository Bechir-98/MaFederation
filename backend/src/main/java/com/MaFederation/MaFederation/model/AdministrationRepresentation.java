package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "administration")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdministrationRepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminID;

    // Foreign key to Personnel
    @Column(nullable = false)
    private Integer personnelID;

    @Lob
    private String remarks;

    // Foreign key to File (nomination letter)
    private Integer nominationLetterFileID;

 
}
