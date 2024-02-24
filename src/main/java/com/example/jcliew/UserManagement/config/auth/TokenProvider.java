package com.example.jcliew.UserManagement.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jcliew.UserManagement.entity.User;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class TokenProvider {
    @Value("${app.security.jwt.token.secret}")
    private String JWT_SECRET;

    public String generateAccessToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("username", user.getUsername())
                    .withExpiresAt(genAccessExpirationDate())
                    .sign(algorithm);
        } catch(JWTCreationException exception){
            throw new JWTCreationException("Error while generating JWT", exception);
        }
    }

    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Error while validating JWT", exception);
        }
    }

    private Instant genAccessExpirationDate() {
        // 2 hours before expiration
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
