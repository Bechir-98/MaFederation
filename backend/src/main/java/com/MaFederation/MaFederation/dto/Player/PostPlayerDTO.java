
package com.MaFederation.MaFederation.dto.Player;

import lombok.*;
import java.math.BigDecimal;


import com.MaFederation.MaFederation.enums.Position;
import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPlayerDTO  extends PostClubMemberDTO {
    private Position position;
    private Integer jerseyNumber;
    private BigDecimal height;
    private BigDecimal weight;
}
