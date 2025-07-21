package com.MaFederation.MaFederation.dto;

import com.MaFederation.MaFederation.enums.FileType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFileDTO {
    private Integer id;
    private String fileUrl;
    private FileType type;   // ✅ now using enum
    private Integer userId;
}
