package com.MaFederation.MaFederation.dto.ClubMember;

import java.util.List;

import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponseClubMemberDTO extends ResponseUserDTO{
    private Integer clubId;
    private String memberType;
    private List<String> categories;
}


