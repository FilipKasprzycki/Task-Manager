package com.example.taskmanager.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtCreator {

    @Inject
    private JwtSignatureKeyProvider jwtSignatureKeyProvider;

    public String createToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .expiration(convertToDate(LocalDateTime.now().plusMinutes(30)))
                .signWith(jwtSignatureKeyProvider.getSignatureKey())
                .compact();
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
