package com.MaFederation.MaFederation.controllers.auth;



import com.MaFederation.MaFederation.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubRegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Integer clubId;
}
