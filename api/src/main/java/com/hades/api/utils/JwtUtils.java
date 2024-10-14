package com.hades.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hades.api.utils.Exceptions.ForbiddenException;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.UUID;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(UUID userUuid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userUuid", userUuid.toString());

        return Jwts
                .builder()
                .setClaims(claims)
                // .setSubject(signupDto.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims isValidToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

            // Parse the token and validate the signature
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims;

        } catch (Exception e) {
            throw new ForbiddenException("Token is invalid");
        }

    }
}
