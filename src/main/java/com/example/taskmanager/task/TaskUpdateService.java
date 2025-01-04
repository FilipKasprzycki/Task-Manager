package com.example.taskmanager.task;

import com.example.taskmanager.api.exception.UserNotFoundException;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.TaskManager;
import com.example.taskmanager.db.manager.UserManager;
import com.example.taskmanager.jwt.AuthorizedUser;
import com.example.taskmanager.task.entity.TaskUpdateApiRequest;
import jakarta.inject.Inject;

class TaskUpdateService {

    @Inject
    private AuthorizedUser authorizedUser;

    @Inject
    private UserManager userManager;

    @Inject
    private TaskManager taskManager;

    void update(TaskUpdateApiRequest request) {
        User user = userManager.findByUuid(authorizedUser.getUserUuid())
                .orElseThrow(() -> new UserNotFoundException(String.format("Not found user with UUID [%s]", authorizedUser.getUserUuid())));

        taskManager.update(user, request);
    }
}
