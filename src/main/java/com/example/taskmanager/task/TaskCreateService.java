package com.example.taskmanager.task;

import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.TaskManager;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import jakarta.inject.Inject;

class TaskCreateService {

    @Inject
    private TaskManager taskManager;

    @Inject
    private TaskMapper taskMapper;

    void create(User user, TaskCreateApiRequest request) {
        Task task = taskMapper.map(request, user);
        taskManager.persist(task);
    }
}
