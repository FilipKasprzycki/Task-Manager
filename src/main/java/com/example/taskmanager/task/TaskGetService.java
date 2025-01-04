package com.example.taskmanager.task;

import com.example.taskmanager.api.exception.UserNotFoundException;
import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.TaskManager;
import com.example.taskmanager.db.manager.UserManager;
import com.example.taskmanager.jwt.AuthorizedUser;
import com.example.taskmanager.task.entity.TaskApiResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.UUID;

class TaskGetService {

    @Inject
    private AuthorizedUser authorizedUser;

    @Inject
    private UserManager userManager;

    @Inject
    private TaskManager taskManager;

    @Inject
    private TaskMapper taskMapper;

    TaskApiResponse get(UUID taskUuid) {
        User user = userManager.findByUuid(authorizedUser.getUserUuid())
                .orElseThrow(() -> new UserNotFoundException(String.format("Not found user with UUID [%s]", authorizedUser.getUserUuid())));

        Task task = taskManager.findByUuidAndUser(taskUuid, user)
                .orElseThrow(() -> new NotFoundException(String.format("Not found task [UUID: %s] for user [UUID: %s]", taskUuid, user.getUuid())));

        return taskMapper.map(task);
    }

    List<TaskApiResponse> getAll() {
        User user = userManager.findByUuid(authorizedUser.getUserUuid())
                .orElseThrow(() -> new UserNotFoundException(String.format("Not found user with UUID [%s]", authorizedUser.getUserUuid())));

        List<Task> tasks = taskManager.findByUser(user);

        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }
}
