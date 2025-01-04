package com.example.taskmanager.api.exception;

import com.example.taskmanager.jwt.exception.JwtAuthorizationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JwtAuthorizationExceptionMapper extends HttpExceptionMapper<JwtAuthorizationException> {

    @Override
    protected Object getExceptionMessage(JwtAuthorizationException exception) {
        return "Authorization failed";
    }

    @Override
    protected Response.Status getResponseStatus() {
        return Response.Status.UNAUTHORIZED;
    }
}
