package com.MaFederation.MaFederation.controllers.auth;
import com.MaFederation.MaFederation.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClubAuthenticationResponse extends AuthenticationResponse {
    private Integer clubId;

    @Builder(builderMethodName = "clubAuthBuilder")
    public ClubAuthenticationResponse(String accessToken,
                                      String refreshToken,
                                      RoleName role,
                                      Integer clubId) {
        super(accessToken, refreshToken, role);
        this.clubId = clubId;
    }
}
