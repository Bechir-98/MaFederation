package com.MaFederation.MaFederation.dto.Staff;

import java.util.List;

import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponceStaffDTO extends  ResponceClubMemberDTO {
    private String specialty;
}
