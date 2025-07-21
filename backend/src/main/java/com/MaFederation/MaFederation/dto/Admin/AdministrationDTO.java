package com.MaFederation.MaFederation.dto.Admin;

import com.MaFederation.MaFederation.dto.ClubMember.ClubMemberDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // include fields from ClubMemberDTO
public class AdministrationDTO extends ClubMemberDTO {
    private String role;
}
