package com.example.taskmanager.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HttpExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

    @Override
    public Response toResponse(E exception) {
        log.warn("Exception {}, message: {}", exception.getClass().getName(), exception.getMessage());
        return Response.status(getResponseStatus())
                .entity(getExceptionMessage(exception))
                .build();
    }

    protected abstract Object getExceptionMessage(E exception);

    protected abstract Response.Status getResponseStatus();
}
