package com.MaFederation.MaFederation.dto.ClubMember;

import java.util.List;

import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
import com.MaFederation.MaFederation.enums.ClubMemberType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ResponseClubMemberDTO extends ResponseUserDTO{

    private Integer clubId;
    private List<String> categories;
    private ClubMemberType type;
}


