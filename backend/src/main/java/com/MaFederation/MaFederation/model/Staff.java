package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@DiscriminatorValue("STAFF")  // Valeur à adapter selon ta logique métier
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff extends ClubMember {

    private String specialty;

    @ManyToMany
    @JoinTable(
        name = "staff_category",
        joinColumns = @JoinColumn(name = "staff_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}
