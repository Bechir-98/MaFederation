package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;



@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Category extends Audit {


    private String name;
    private String description;
    private Integer ageMin;
    private Integer ageMax;

    @ManyToMany(mappedBy = "categories")
    private List<ClubMember> clubMembers;

    

    @ManyToMany(mappedBy = "categories")
    private List<Club> clubs;
}
