package com.MaFederation.MaFederation.dto.User;

import com.MaFederation.MaFederation.enums.FileType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFileDTO {


    private Integer id;
    private byte[] content;
    private FileType type;   
    private Integer userId;
}
