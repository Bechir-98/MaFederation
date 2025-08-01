package com.MaFederation.MaFederation.dto.Club;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.MaFederation.MaFederation.enums.ClubFileType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubFileDTO {
    private Integer id;
    private ClubFileType type;
    private Integer clubId;  // add this
}
