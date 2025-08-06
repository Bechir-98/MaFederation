package com.MaFederation.MaFederation.dto.Player;

import lombok.*;
import java.math.BigDecimal;


import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
import com.MaFederation.MaFederation.enums.Position;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // include fields from ClubMemberDTO + UserDTO
@Builder
public class ResponsePlayerDTO extends ResponseClubMemberDTO {
    private Position position;
    private Integer jerseyNumber;
    private BigDecimal height;
    private BigDecimal weight;
}
