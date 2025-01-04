package com.example.taskmanager.task;

import com.example.taskmanager.api.exception.UserNotFoundException;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.UserManager;
import com.example.taskmanager.jwt.AuthorizedUser;
import com.example.taskmanager.task.entity.TaskApiResponse;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import com.example.taskmanager.task.entity.TaskUpdateApiRequest;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

public class TaskService {

    @Inject
    private AuthorizedUser authorizedUser;

    @Inject
    private UserManager userManager;

    @Inject
    private TaskCreateService taskCreateService;

    @Inject
    private TaskGetService taskGetService;

    @Inject
    private TaskDeleteService taskDeleteService;

    @Inject
    private TaskUpdateService taskUpdateService;

    public void create(TaskCreateApiRequest request) {
        taskCreateService.create(findAuthorizedUser(), request);
    }

    public TaskApiResponse get(UUID taskUuid) {
        return taskGetService.get(findAuthorizedUser(), taskUuid);
    }

    public List<TaskApiResponse> getAll() {
        return taskGetService.getAll(findAuthorizedUser());
    }

    public void delete(UUID uuid) {
        taskDeleteService.delete(findAuthorizedUser(), uuid);
    }

    public void update(TaskUpdateApiRequest request) {
        taskUpdateService.update(findAuthorizedUser(), request);
    }

    private User findAuthorizedUser() {
        return userManager.findByUuid(authorizedUser.getUserUuid())
                .orElseThrow(() -> new UserNotFoundException(String.format("Not found user with UUID [%s]", authorizedUser.getUserUuid())));
    }
}
