package com.example.taskmanager.jwt;

import com.example.taskmanager.jwt.exception.JwtAuthorizationException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;

@Slf4j
@Provider
@JwtAuth
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {

    private static final String BEARER = "Bearer ";

    @Inject
    private JwtSignatureKeyProvider jwtSignatureKeyProvider;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        log.info("Authorizing request to secure context [{} {}]", requestContext.getMethod(), requestContext.getUriInfo().getAbsolutePath().toString());
        String jwt = getJwtFromHeader(requestContext);
        SecretKey signatureKey = jwtSignatureKeyProvider.getSignatureKey();
        String email = getSubject(jwt, signatureKey);

        log.info("JWT subject: [{}]", email);
    }

    private String getSubject(String jwt, SecretKey signatureKey) {
        try {
            return Jwts.parser()
                    .verifyWith(signatureKey)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            log.warn("JWT signature is not correct");
            throw new NotAuthorizedException("JWT signature is not correct");
        }
    }

    private String getJwtFromHeader(ContainerRequestContext requestContext) {
        String authorizationHeader = getAuthorizationHeader(requestContext);
        return getJwtFromAuthorizationHeader(authorizationHeader);
    }

    private String getAuthorizationHeader(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(authorizationHeader)) {
            throw new JwtAuthorizationException("Authorization header not provided");
        }

        return authorizationHeader;
    }

    private String getJwtFromAuthorizationHeader(String authorizationHeader) {
        if (!authorizationHeader.startsWith(BEARER)) {
            throw new JwtAuthorizationException("\"Authorization header not starts with ''" + BEARER + "'");
        }

        return authorizationHeader.substring(BEARER.length()).trim();
    }
}
