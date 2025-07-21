package com.MaFederation.MaFederation.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // include fields from ClubMemberDTO
public class AdministrationDTO extends ClubMemberDTO {
    private String role;
}
