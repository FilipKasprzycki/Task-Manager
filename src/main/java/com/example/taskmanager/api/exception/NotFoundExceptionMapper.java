package com.example.taskmanager.api.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper extends HttpExceptionMapper<NotFoundException> {

    @Override
    Object getExceptionMessage(NotFoundException exception) {
        return null;
    }

    @Override
    Response.Status getResponseStatus() {
        return Response.Status.NOT_FOUND;
    }
}
