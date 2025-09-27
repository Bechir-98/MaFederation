package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.enums.RoleName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    // -----------------------------
    // 🔹 Extractors
    // -----------------------------
    @Transactional
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Integer extractClubId(String token) {
        return extractClaim(token, claims -> claims.get("clubId", Integer.class));
    }
    public RoleName extractRole(String token) {
        return extractClaim(token, claims -> RoleName.valueOf(claims.get("role", String.class)));
    }


    // -----------------------------
    // 🔹 Token Generators
    // -----------------------------
    @Transactional
    public String generateUserToken(UserDetails userDetails,RoleName role) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role",role.toString());
        return generateToken(extraClaims, userDetails, jwtExpiration);

    }
    @Transactional
    public String generateClubMemberToken(UserDetails userDetails, Integer clubId  , RoleName role) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("clubId", clubId);
        extraClaims.put("role",role.toString());
        return generateToken(extraClaims, userDetails, jwtExpiration);
    }
    @Transactional
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // -----------------------------
    // 🔹 Validation
    // -----------------------------
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // -----------------------------
    // 🔹 Claims
    // -----------------------------
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
