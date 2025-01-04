package com.example.taskmanager.task.exception;

import com.example.taskmanager.api.exception.HttpExceptionMapper;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidDateFormatExceptionMapper extends HttpExceptionMapper<InvalidDateFormatException> {

    @Override
    protected Object getExceptionMessage(InvalidDateFormatException exception) {
        return "Given deadline date is in invalid format. Valid format is yyyy-MM-ddThh:mm:ss (ISO-8601) eg. 2011-12-03T10:15:30";
    }

    @Override
    protected Response.Status getResponseStatus() {
        return Response.Status.BAD_REQUEST;
    }
}
