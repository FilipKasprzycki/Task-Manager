package com.example.taskmanager.api.exception;

import com.example.taskmanager.auth.exception.UserRegisterException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserRegisterExceptionMapper extends HttpExceptionMapper<UserRegisterException> {

    @Override
    protected Object getExceptionMessage(UserRegisterException exception) {
        return "Registration failed - user already exists";
    }

    @Override
    protected Response.Status getResponseStatus() {
        return Response.Status.FORBIDDEN;
    }


}
