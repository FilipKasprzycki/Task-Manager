package com.example.taskmanager.api.resource;

import com.example.taskmanager.jwt.SecuredByJWT;
import com.example.taskmanager.task.TaskService;
import com.example.taskmanager.task.entity.TaskApiResponse;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Path("/tasks")
@SecuredByJWT
public class TaskResource {

    @Context
    private HttpServletRequest context;

    @Inject
    private TaskService taskService;

    @POST
    public Response create(TaskCreateApiRequest request) {
        log.info("[{}] {} {}", context.getMethod(), context.getPathInfo(), request);
        taskService.create(request);
        return Response.ok().build();
    }

    @GET
    @Path("/{uuid}")
    public TaskApiResponse get(@Valid @NotNull @PathParam("uuid") UUID taskUuid) {
        log.info("[{}] {}/{}", context.getMethod(), context.getPathInfo(), taskUuid);
        TaskApiResponse response = taskService.get(taskUuid);
        log.info("Response: {}", response);
        return response;
    }
}
