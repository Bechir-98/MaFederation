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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
}
