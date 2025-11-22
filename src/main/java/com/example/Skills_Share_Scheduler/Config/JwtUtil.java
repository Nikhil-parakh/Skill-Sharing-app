package com.example.Skills_Share_Scheduler.Config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    @Value("${jwt.issuer}")
    private String issuer;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();  // extract the username from Token
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();  // extract the expiration time from token
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // check for expiry of token 
    }

    public boolean validateToken(String token, String expectedUsername) {  // validate the token from our user's token
        String username = extractUsername(token);
        return username.equals(expectedUsername) && !isTokenExpired(token);
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();  // it will initiate the process of generating token (call's createToken)
        return createToken(claims, username);
    }

    @SuppressWarnings("deprecation")
    private String createToken(Map<String, Object> claims, String username) {  // the actuall token is generated here 
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuer(issuer)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {    //takes the String (token) and convert it into claims object
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
