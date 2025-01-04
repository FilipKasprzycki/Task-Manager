package com.example.taskmanager.task;

import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.TaskManager;
import com.example.taskmanager.task.entity.TaskUpdateApiRequest;
import jakarta.inject.Inject;

class TaskUpdateService {

    @Inject
    private TaskManager taskManager;

    void update(User user, TaskUpdateApiRequest request) {
        taskManager.update(user, request);
    }
}
