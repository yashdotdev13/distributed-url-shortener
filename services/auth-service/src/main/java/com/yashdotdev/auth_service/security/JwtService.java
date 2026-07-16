package com.yashdotdev.auth_service.security;


import com.yashdotdev.auth_service.dtos.response.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;



    public String generateAccessToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof CustomUserDetails customUserDetails) {

            claims.put("userId", customUserDetails.getUser().getId());

            claims.put(
                    "roles",
                    customUserDetails.getAuthorities()
                            .stream()
                            .map(authority -> authority.getAuthority())
                            .toList()
            );
        }

        return generateToken(
                claims,
                userDetails,
                accessTokenExpiration
        );
    }


    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }


    public TokenResponse generateTokens(UserDetails userDetails) {

        return TokenResponse.builder()
                .accessToken(generateAccessToken(userDetails))
                .refreshToken(generateRefreshToken(userDetails))
                .tokenType("Bearer")
                .expiresIn(accessTokenExpiration / 1000)
                .build();
    }



    private String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }



    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        return extractUsername(token).equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }


    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }


    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }



    public Instant getRefreshTokenExpiry() {
        return Instant.now()
                .plusMillis(refreshTokenExpiration);

    }


    public Long extractUserId(String token) {

        return extractClaim(
                token,
                claims -> claims.get("userId", Long.class)
        );

    }

    public java.util.List<String> extractRoles(String token) {

        return extractClaim(
                token,
                claims -> claims.get("roles", java.util.List.class)
        );

    }
}