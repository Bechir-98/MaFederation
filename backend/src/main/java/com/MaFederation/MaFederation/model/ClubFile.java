package com.MaFederation.MaFederation.model;

import com.MaFederation.MaFederation.enums.ClubFileType;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "club_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClubFile extends Audit{


    private ClubFileType type;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] content;

    
    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;


}
