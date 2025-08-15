package com.MaFederation.MaFederation.dto.mod;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import com.MaFederation.MaFederation.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModDTO {

    private Integer id; // Moderator-specific ID
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;
    private String nationality;
    private String nationalID;
    private Set<Role> roles;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
