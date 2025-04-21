package com.license.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.license.backend.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenProvider {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public String generateAccessToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        return JWT.create()
                .withSubject(user.getUserId().toString())
                .withExpiresAt(genAccessExpirationDate())
                .sign(algorithm);
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating token", exception);
        }
    }

    private Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public Integer decodeId(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            String subject = JWT.require(algorithm)
                    .build()
                    .verify(token.substring(7))
                    .getSubject();
            return Integer.parseInt(subject);
        } catch (JWTVerificationException | NumberFormatException e) {
            throw new RuntimeException("Invalid token or ID format", e);
        }
    }

}
