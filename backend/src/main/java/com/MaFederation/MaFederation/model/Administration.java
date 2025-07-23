package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("ADMIN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administration extends ClubMember {

    @Column(nullable = false)
    private String role;
}
