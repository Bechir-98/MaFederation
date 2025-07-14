package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffRepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffID;

    // Foreign key to Personnel
    @Column(nullable = false)
    private Integer personnelID;

    private String status;

    private String specialty;


}
