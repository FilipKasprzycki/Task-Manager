package com.example.taskmanager.task;

import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.TaskManager;
import jakarta.inject.Inject;

import java.util.UUID;

class TaskDeleteService {

    @Inject
    private TaskManager taskManager;

    void delete(User user, UUID uuid) {
        taskManager.delete(uuid, user);
    }
}
