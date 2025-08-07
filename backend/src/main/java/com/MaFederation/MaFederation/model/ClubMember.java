package com.MaFederation.MaFederation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ClubMember extends User {


    @ManyToOne
    @JoinColumn(name = "club_id")
    @JsonIgnore
    private Club club;

    @ManyToMany
    @JoinTable(
        name = "member_category",
        joinColumns = @JoinColumn(name = "member_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;








    
}
