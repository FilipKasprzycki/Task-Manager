package com.example.taskmanager.api;

import com.example.taskmanager.auth.AuthApiRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/auth")
public class AuthResource {

    @Context
    private HttpServletRequest context;

    @POST
    @Path("/register")
    public Response register(@Valid AuthApiRequest request) {
        log.info("[{}] {} {}", context.getMethod(), context.getPathInfo(), request);
        return Response.ok().build();
    }
}
