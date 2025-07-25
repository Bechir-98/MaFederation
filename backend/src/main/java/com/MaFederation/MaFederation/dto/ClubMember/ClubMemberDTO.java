package com.MaFederation.MaFederation.dto.ClubMember;

import com.MaFederation.MaFederation.dto.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClubMemberDTO extends UserDTO {
    private String role;
    private Integer clubId;
    private String type; // ✅ Needed for polymorphic deserialization and mapping
}
