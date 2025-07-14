package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id") // important pour l’héritage depuis ClubMember → User
public class Staff extends ClubMember {

    private String specialty;

    @ManyToMany
    @JoinTable(
        name = "staff_category", // ⚠️ modifié pour éviter de partager la même table que Player
        joinColumns = @JoinColumn(name = "staff_id"),           // correspond à user_id de Staff
        inverseJoinColumns = @JoinColumn(name = "category_id")  // FK vers Category
    )
    private List<Category> categories;
}
