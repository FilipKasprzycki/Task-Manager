package com.example.taskmanager.task;

import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import jakarta.inject.Inject;

public class TaskService {

    @Inject
    private TaskCreateService taskCreateService;

    public void create(TaskCreateApiRequest request) {
        taskCreateService.create(request);
    }
}
