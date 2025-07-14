package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administrations") // nom pluriel recommandé
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id") // héritage basé sur la clé primaire de User
public class Administration extends ClubMember {

    @Column(nullable = false)
    private String role;
}
