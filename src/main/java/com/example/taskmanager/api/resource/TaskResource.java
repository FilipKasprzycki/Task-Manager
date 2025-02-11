package com.example.taskmanager.api.resource;

import com.example.taskmanager.jwt.SecuredByJWT;
import com.example.taskmanager.task.TaskService;
import com.example.taskmanager.task.entity.TaskApiResponse;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import com.example.taskmanager.task.entity.TaskUpdateApiRequest;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/tasks")
@SecuredByJWT
public class TaskResource {

    @Context
    private HttpServletRequest context;

    @Inject
    private TaskService taskService;

    @POST
    public Response create(@Valid TaskCreateApiRequest request) {
        log.info("[{}] {} {}", context.getMethod(), context.getPathInfo(), request);
        taskService.create(request);
        return Response.ok().build();
    }

    @GET
    @Path("/{uuid}")
    public TaskApiResponse get(@PathParam("uuid") UUID uuid) {
        log.info("[{}] {}", context.getMethod(), context.getPathInfo());
        TaskApiResponse response = taskService.get(uuid);
        log.info("Response: {}", response);
        return response;
    }

    @GET
    public List<TaskApiResponse> getAll(
            @QueryParam("completed") Boolean isCompleted,
            @QueryParam("deadlineFrom") String deadlineFrom,
            @QueryParam("deadlineTo") String deadlineTo
    ) {
        log.info("[{}] {}", context.getMethod(), context.getPathInfo());
        List<TaskApiResponse> response = taskService.getAll(isCompleted, deadlineFrom, deadlineTo);
        log.info("Response: {} tasks", response.size());
        return response;
    }

    @DELETE
    @Path("/{uuid}")
    public Response delete(@PathParam("uuid") UUID uuid) {
        log.info("[{}] {}", context.getMethod(), context.getPathInfo());
        taskService.delete(uuid);
        log.info("Task [UUID: {}] removed", uuid);
        return Response.ok().build();
    }

    @PUT
    public Response update(@Valid TaskUpdateApiRequest request) {
        log.info("[{}] {} {}", context.getMethod(), context.getPathInfo(), request);
        taskService.update(request);
        log.info("Task [UUID: {}] updated", request.getUuid());
        return Response.ok().build();
    }
}
