package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("CLUB_MEMBER") // valeur du discriminant pour ClubMember
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubMember extends User {

    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;
}
