package com.example.taskmanager.api.exception;

import com.example.taskmanager.auth.exception.UserLoginException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserLoginExceptionMapper extends HttpExceptionMapper<UserLoginException> {

    @Override
    Response.Status getResponseStatus() {
        return Response.Status.FORBIDDEN;
    }

    @Override
    Object getExceptionMessage(UserLoginException exception) {
        return "Login failed - invalid email or password";
    }
}
