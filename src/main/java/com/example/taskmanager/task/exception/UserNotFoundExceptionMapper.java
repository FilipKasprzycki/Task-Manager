package com.example.taskmanager.task.exception;

import com.example.taskmanager.api.exception.HttpExceptionMapper;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserNotFoundExceptionMapper extends HttpExceptionMapper<UserNotFoundException> {

    @Override
    protected Object getExceptionMessage(UserNotFoundException exception) {
        return "Unauthorized";
    }

    @Override
    protected Response.Status getResponseStatus() {
        return Response.Status.UNAUTHORIZED;
    }


}
