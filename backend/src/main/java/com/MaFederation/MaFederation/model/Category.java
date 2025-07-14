package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories") // nom pluriel recommandé pour éviter ambiguïté
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryID;

    private String name;

    private String description;

    private Integer ageMin;

    private Integer ageMax;

    @ManyToMany(mappedBy = "categories")
    private List<Player> players;

    @ManyToMany(mappedBy = "categories")
    private List<Staff> staff;
}
