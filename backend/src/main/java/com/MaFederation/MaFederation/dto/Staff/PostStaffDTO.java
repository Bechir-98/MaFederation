package com.MaFederation.MaFederation.dto.Staff;


import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostStaffDTO extends PostClubMemberDTO {
    private String specialty;
}
