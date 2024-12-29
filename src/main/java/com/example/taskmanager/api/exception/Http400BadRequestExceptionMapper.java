package com.example.taskmanager.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class Http400BadRequestExceptionMapper extends HttpExceptionMapper<Http400BadRequestException> {

    @Override
    Response.Status getResponseStatus() {
        return Response.Status.BAD_REQUEST;
    }
}
