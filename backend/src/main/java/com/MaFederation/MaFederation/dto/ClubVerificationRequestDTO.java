package com.MaFederation.MaFederation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubVerificationRequestDTO {
    private Integer id;       
    private Integer clubId;
    private String clubName;
    private String createdAt;
}
