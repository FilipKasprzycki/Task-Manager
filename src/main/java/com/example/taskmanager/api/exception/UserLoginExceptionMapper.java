package com.example.taskmanager.api.exception;

import com.example.taskmanager.auth.exception.UserLoginException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserLoginExceptionMapper extends HttpExceptionMapper<UserLoginException> {

    @Override
    protected Response.Status getResponseStatus() {
        return Response.Status.FORBIDDEN;
    }

    @Override
    protected Object getExceptionMessage(UserLoginException exception) {
        return "Login failed - invalid email or password";
    }
}
