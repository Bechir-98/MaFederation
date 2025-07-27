package com.MaFederation.MaFederation.dto.ClubMember;

import java.util.List;

import com.MaFederation.MaFederation.dto.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostClubMemberDTO extends UserDTO {
    private Integer clubId;
    private String type; 
    private List<Integer> categoryIds;
}
