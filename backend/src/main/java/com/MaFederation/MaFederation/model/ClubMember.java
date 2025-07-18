package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubMember extends User {

    private String position;


    @ManyToOne()
    @JoinColumn(name = "club_id")
    private Club club;

    

}
