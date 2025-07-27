package com.MaFederation.MaFederation.dto.Admin;

import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // include fields from ClubMemberDTO
public class ResponceAdministrationDTO extends  ResponceClubMemberDTO {
    private String role;
}
