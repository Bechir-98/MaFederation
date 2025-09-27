package com.MaFederation.MaFederation.model;

import java.util.List;

import com.MaFederation.MaFederation.enums.ClubMemberType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@Data
@NoArgsConstructor

@SuperBuilder
public abstract class ClubMember extends User {


    @ManyToOne
    @JoinColumn(name = "club_id")
    @JsonIgnore
    private Club club;

    // @Column(name = "type", updatable = false)
    private ClubMemberType type;

    @ManyToMany
    @JoinTable(
        name = "member_category",
        joinColumns = @JoinColumn(name = "member_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;








    
}
