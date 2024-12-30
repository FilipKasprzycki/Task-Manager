package com.example.taskmanager.api.resource;

import com.example.taskmanager.auth.AuthService;
import com.example.taskmanager.auth.entity.AuthApiRequest;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/auth")
public class AuthResource {

    @Context
    private HttpServletRequest context;

    @Inject
    private AuthService authService;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@Valid AuthApiRequest request) {
        log.info("[{}] {} {}", context.getMethod(), context.getPathInfo(), request);

        authService.register(request);

        return Response.ok().build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid AuthApiRequest request) {
        log.info("[{}] {} {}", context.getMethod(), context.getPathInfo(), request);

        authService.login(request);

        return Response.ok().build();
    }
}
