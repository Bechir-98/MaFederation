package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administration extends ClubMember {

    private String post;
}
