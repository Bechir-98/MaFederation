package com.MaFederation.MaFederation.dto.ClubMember;

import java.util.List;

import com.MaFederation.MaFederation.dto.User.ResponceUserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponceClubMemberDTO extends ResponceUserDTO{
    private Integer clubId;
    private String type;
    private List<Integer> categoryIds;
}


