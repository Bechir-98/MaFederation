package com.MaFederation.MaFederation.model;

import com.MaFederation.MaFederation.enums.ClubFileType;

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

    private ClubFileType type;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] content;


    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;


}
