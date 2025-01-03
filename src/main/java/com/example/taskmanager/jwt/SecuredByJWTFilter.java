package com.example.taskmanager.jwt;

import com.example.taskmanager.jwt.exception.JwtAuthorizationException;
import com.example.taskmanager.utils.DateConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Provider
@SecuredByJWT
@Priority(Priorities.AUTHENTICATION)
public class SecuredByJWTFilter implements ContainerRequestFilter {

    private static final String BEARER = "Bearer ";
    private static final String HMAC_SHA512 = "HS512";

    @Inject
    private JwtSignatureKeyProvider jwtSignatureKeyProvider;

    @Inject
    private DateConverter dateConverter;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        log.info("Authorizing request to secure context [{} {}]", requestContext.getMethod(), requestContext.getUriInfo().getAbsolutePath().toString());
        String jwt = getJwtFromHeader(requestContext);
        SecretKey signatureKey = jwtSignatureKeyProvider.getSignatureKey();
        validate(jwt, signatureKey);
    }

    private void validate(String jwt, SecretKey signatureKey) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(signatureKey)
                    .build()
                    .parseSignedClaims(jwt);

            validateHeader(claims.getHeader());
            String subject = validatePayload(claims.getPayload());
            log.info("JWT for [{}] is correct", subject);
        } catch (Exception e) {
            throw new JwtAuthorizationException(String.format("Exception: %s, message: %s", e.getClass().getName(), e.getMessage()));
        }
    }

    private void validateHeader(JwsHeader header) {
        if (header == null) {
            throw new JwtAuthorizationException("JWT header is null");
        }
        if (!HMAC_SHA512.equals(header.getAlgorithm())) {
            throw new JwtAuthorizationException(String.format("Algorithm in header is not correct. Expected [%s], actual [%s]", HMAC_SHA512, header.getAlgorithm()));
        }
    }

    private String validatePayload(Claims payload) {
        if (payload == null) {
            throw new JwtAuthorizationException("JWT payload is null");
        }

        Date expirationDate = payload.getExpiration();
        if (expirationDate == null || expirationDate.before(dateConverter.toDate(LocalDateTime.now()))) {
            throw new JwtAuthorizationException("Expiration date is invalid");
        }

        String subject = payload.getSubject();
        if (StringUtils.isBlank(subject)) {
            throw new JwtAuthorizationException("Subject not provided");
        }
        return subject;
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
            throw new JwtAuthorizationException("Authorization header not starts with ''" + BEARER + "'");
        }

        return authorizationHeader.substring(BEARER.length()).trim();
    }
}
