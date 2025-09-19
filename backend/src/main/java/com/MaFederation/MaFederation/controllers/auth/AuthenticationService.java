package com.MaFederation.MaFederation.controllers.auth;

import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.enums.RoleName;
import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.model.Token;
import com.MaFederation.MaFederation.model.TokenType;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.repository.UserRepository;
import com.MaFederation.MaFederation.repository.auth.TokenRepository;
import com.MaFederation.MaFederation.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClubRepository clubRepository;

    // -------------------------
    // 🔹 Registration
    // -------------------------
    public void register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleName.ADMIN)
                .build();

        repository.save(user);
    }

    public void registerClubAdmin(ClubRegisterRequest request) {
        var club = clubRepository.findById(request.getClubId())
                .orElseThrow(() -> new RuntimeException("Club not found"));

        Administration user = Administration.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleName.CLUB_ADMIN)
                .club(club)
                .build();

        repository.save(user);
    }

    // -------------------------
    // 🔹 Authentication
    // -------------------------
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // 1️⃣ Authenticate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2️⃣ Fetch user
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        RoleName role =user.getRole();

                String jwtToken;
        String refreshToken;

        // 3️⃣ Generate JWT (clubId is added to claims if user is an Administration)
        if (user instanceof Administration adminUser) {
            Integer clubId = (adminUser.getClub() != null) ? adminUser.getClub().getId() : null;
            jwtToken = jwtService.generateClubMemberToken(user, clubId,role);
        } else {
            jwtToken = jwtService.generateUserToken(user,role);
        }
        refreshToken = jwtService.generateRefreshToken(user);

        // revoke old tokens & save new
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        // 4️⃣ Return unified response
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    // -------------------------
    // 🔹 Token persistence
    // -------------------------
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    // -------------------------
    // 🔹 Refresh token flow
    // -------------------------
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            RoleName role =user.getRole();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken;
                if (user instanceof Administration adminUser) {
                    Integer clubId = (adminUser.getClub() != null) ? adminUser.getClub().getId() : null;
                    accessToken = jwtService.generateClubMemberToken(user, clubId,role);
                } else {
                    accessToken = jwtService.generateUserToken(user,role);
                }

                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
