package com.MaFederation.MaFederation.dto.VerificationRequestResponseDTO;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationRequestResponseDTO {
    private Integer id;
    private Integer userId;
    private String userName; // optional: firstName + lastName
    private String targetType;
    private Integer clubId;
    private String clubName;
   protected LocalDateTime createdAt;

}
