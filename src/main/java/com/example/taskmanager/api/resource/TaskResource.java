package com.example.taskmanager.api.resource;

import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.manager.TaskManager;
import com.example.taskmanager.jwt.SecuredByJWT;
import com.example.taskmanager.task.TaskService;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

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
}
