package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import com.MaFederation.MaFederation.enums.Position;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player extends ClubMember {

    @Enumerated(EnumType.STRING)
    private Position position;
    private Integer jerseyNumber;
    private BigDecimal height;
    private BigDecimal weight;
    private Integer licenseNumber;

    
}
