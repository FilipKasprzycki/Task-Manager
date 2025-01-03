package com.example.taskmanager.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserNotFoundExceptionMapper extends HttpExceptionMapper<UserNotFoundException> {

    @Override
    Object getExceptionMessage(UserNotFoundException exception) {
        return "Unauthorized";
    }

    @Override
    Response.Status getResponseStatus() {
        return Response.Status.UNAUTHORIZED;
    }


}
