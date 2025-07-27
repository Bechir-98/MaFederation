package com.MaFederation.MaFederation.dto.Admin;
import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // include fields from ClubMemberDTO
public class PostAdminstrationDTO extends PostClubMemberDTO {
        private String role;

}
