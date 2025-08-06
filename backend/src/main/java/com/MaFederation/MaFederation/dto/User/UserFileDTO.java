package com.MaFederation.MaFederation.dto.User;

import com.MaFederation.MaFederation.enums.UserFileType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFileDTO {


    private Integer id;
    private UserFileType type;   
    private Integer userId;
}
