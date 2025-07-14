package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id") // important pour l’héritage JPA
public class Player extends ClubMember {

    private Integer jerseyNumber;

    private BigDecimal height;

    private BigDecimal weight;

    @ManyToMany
    @JoinTable(
        name = "player_category",
        joinColumns = @JoinColumn(name = "player_id"),           // FK vers Player (user_id)
        inverseJoinColumns = @JoinColumn(name = "category_id")   // FK vers Category
    )
    private List<Category> categories;
}
