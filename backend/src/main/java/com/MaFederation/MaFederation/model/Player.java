package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player extends ClubMember {

    private Integer jerseyNumber;

    private BigDecimal height;

    private BigDecimal weight;

     private Integer licenseNumber;

    @OneToMany
    @JoinTable(
        name = "player_category",
        joinColumns = @JoinColumn(name = "player_id"),        
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

}
