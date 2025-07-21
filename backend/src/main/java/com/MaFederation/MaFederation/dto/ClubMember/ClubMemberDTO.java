package com.MaFederation.MaFederation.dto;

import com.MaFederation.MaFederation.dto.User.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClubMemberDTO extends UserDTO {
    private String role;
    private Integer clubId; 
    
    // référence à Club par ID
}
