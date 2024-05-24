package com.smsoft.greenmromobile.global.jwt;

import com.smsoft.greenmromobile.global.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
    private final Key secretKey;
    private final long expiration;
    private final long refreshTokenExpiration;

    public JwtUtil(@Value("${jwt.secret}")String secret, @Value("${jwt.expiration}") long expiration, @Value("${jwt.refreshTokenExpiration}") long refreshTokenExpiration) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(byteSecretKey);
        this.expiration = expiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateTokenWithDifferentExpiration(UserDetails userDetails, long expirationSeconds) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(createClaims(userDetails))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationSeconds * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public long getRefreshTokenExpirationTime() {
        return refreshTokenExpiration;
    }

    private Claims createClaims(UserDetails userDetails) {
        Claims claims = Jwts.claims();
        claims.put("username", userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return claims;
    }

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();

        Claims claims = Jwts.claims();
        claims.put("username", userDetails.getUsername());
        claims.put("urefItem", ((CustomUserDetails) userDetails).getUrefitem());
        claims.put("ucompanyRef", ((CustomUserDetails) userDetails).getUcompanyRef());
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = extractAllClaims(token);

        List<String> roles = claims.get("roles", List.class);
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public String getUsernameFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("username", String.class);
    }

    public Long getUrefItemFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("urefItem", Long.class);
    }

    public Long getUcompanyRefFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("ucompanyRef", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
