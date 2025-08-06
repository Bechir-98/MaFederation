package com.MaFederation.MaFederation.dto.Admin;

import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // include fields from ClubMemberDTO
public class ResponseAdministrationDTO extends  ResponseClubMemberDTO {
    private String role;
}
