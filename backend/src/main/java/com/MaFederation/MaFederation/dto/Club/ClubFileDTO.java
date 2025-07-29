package com.MaFederation.MaFederation.dto.Club;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.MaFederation.MaFederation.enums.FileType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubFileDTO {
    
    private int id;
    private FileType type;
    private String content; 

}
