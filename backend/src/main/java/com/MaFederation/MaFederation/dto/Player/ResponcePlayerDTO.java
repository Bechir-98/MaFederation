package com.MaFederation.MaFederation.dto.Player;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

import com.MaFederation.MaFederation.dto.ClubMember.ResponceClubMemberDTO;
import com.MaFederation.MaFederation.enums.Position;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // include fields from ClubMemberDTO + UserDTO
@Builder
public class ResponcePlayerDTO extends ResponceClubMemberDTO {
    private Position position;
    private Integer jerseyNumber;
    private BigDecimal height;
    private BigDecimal weight;
}
