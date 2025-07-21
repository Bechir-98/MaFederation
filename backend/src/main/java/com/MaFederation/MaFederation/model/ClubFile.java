package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "club_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String licenseUrl;
    private String logoUrl;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;


}
