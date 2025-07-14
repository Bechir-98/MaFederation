package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CategoryRepresntation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryID;

    // Foreign key to Season
    private Integer seasonID;

    private String name;

    private String description;

    private Integer ageMin;

    private Integer ageMax;


}
