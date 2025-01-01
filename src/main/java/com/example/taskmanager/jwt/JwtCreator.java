package com.example.taskmanager.jwt;

import com.example.taskmanager.utils.DateConverter;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

public class JwtCreator {

    @Inject
    private JwtSignatureKeyProvider jwtSignatureKeyProvider;

    @Inject
    private DateConverter dateConverter;

    public String createToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .expiration(dateConverter.toDate(LocalDateTime.now().plusMinutes(30)))
                .signWith(jwtSignatureKeyProvider.getSignatureKey())
                .compact();
    }
}
