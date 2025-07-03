package com.suryapropertyconsultant.suryapropertyconsultant.Utility;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser; // Import your AdminUser entity
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails; // If you want to use UserDetails as parameter type
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // Make sure you have this in application.properties
    private String SECRET_KEY;

    // Token validity in milliseconds (e.g., 24 hours)
    @Value("${jwt.expiration}") // Make sure you have this in application.properties
    private long EXPIRATION_TIME;

    // --- Generate Token ---
    // Change the parameter type to AdminUser or UserDetails
    public String generateToken(AdminUser adminUser) {
        Map<String, Object> claims = new HashMap<>();
        // Add custom claims from AdminUser
        claims.put("role", adminUser.getRole()); // Add role as a claim
        // You can add more claims like userId, etc.
        return createToken(claims, adminUser.getEmail()); // Use email as the subject
    }

    // This method remains largely the same
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // The subject is typically the username/email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Token expiration
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // --- Key Management ---
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // --- Extract Claims ---
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Subject is the username/email
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // --- Token Validation ---
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Overload for simpler validation (if you don't have UserDetails available)
    // You might use this in JwtAuthFilter if you just want to check validity before loading user
    public Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            // Log the exception (e.g., token parsing error, signature mismatch)
            System.err.println("JWT Validation Error: " + e.getMessage());
            return false;
        }
    }
}