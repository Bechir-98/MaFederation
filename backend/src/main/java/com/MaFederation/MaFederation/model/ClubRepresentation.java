package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClubRepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clubID;

    private String name;
    private String location;
    private Integer foundedYear;

    private String contactEmail;
    private String contactPhone;

    private String bankAccount;
    private String bankName;






}
