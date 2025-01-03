package com.example.taskmanager.task;

import com.example.taskmanager.api.exception.UserNotFoundException;
import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.TaskManager;
import com.example.taskmanager.db.manager.UserManager;
import com.example.taskmanager.jwt.AuthorizedUser;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import jakarta.inject.Inject;

class TaskCreateService {

    @Inject
    private AuthorizedUser authorizedUser;

    @Inject
    private UserManager userManager;

    @Inject
    private TaskManager taskManager;

    @Inject
    private TaskMapper taskMapper;

    void create(TaskCreateApiRequest request) {
        User user = userManager.findByUuid(authorizedUser.getUserUuid())
                .orElseThrow(() -> new UserNotFoundException(String.format("Not found user with UUID [%s]", authorizedUser.getUserUuid())));

        Task task = taskMapper.map(request, user);
        taskManager.persist(task);
    }
}
