package com.MaFederation.MaFederation.dto.Staff;



import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponceStaffDTO extends  ResponseClubMemberDTO {
    private String specialty;
}
