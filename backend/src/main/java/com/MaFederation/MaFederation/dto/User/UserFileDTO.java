package com.MaFederation.MaFederation.dto.User;

import com.MaFederation.MaFederation.enums.PlayerFileType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFileDTO {


    private Integer id;
    private byte[] content;
    private PlayerFileType type;   
    private Integer userId;
}
