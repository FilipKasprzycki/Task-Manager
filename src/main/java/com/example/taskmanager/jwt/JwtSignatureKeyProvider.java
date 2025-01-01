package com.example.taskmanager.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

import javax.crypto.SecretKey;

@Getter
@ApplicationScoped
@Startup
class JwtSignatureKeyProvider {

    private final SecretKey signatureKey = Jwts.SIG.HS512.key().build();
}