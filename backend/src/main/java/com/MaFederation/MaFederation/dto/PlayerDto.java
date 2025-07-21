package com.MaFederation.MaFederation.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import com.MaFederation.MaFederation.enums.Position;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // include fields from ClubMemberDTO + UserDTO
@Builder
public class PlayerDTO extends ClubMemberDTO {
    private Position position;
    private Integer jerseyNumber;
    private BigDecimal height;
    private BigDecimal weight;
    private List<Integer> categoryIds;  // list of associated category IDs
}
