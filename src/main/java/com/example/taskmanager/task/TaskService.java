package com.example.taskmanager.task;

import com.example.taskmanager.task.entity.TaskApiResponse;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import jakarta.inject.Inject;

import java.util.UUID;

public class TaskService {

    @Inject
    private TaskCreateService taskCreateService;

    @Inject
    private TaskGetService taskGetService;

    public void create(TaskCreateApiRequest request) {
        taskCreateService.create(request);
    }

    public TaskApiResponse get(UUID taskUuid) {
        return taskGetService.get(taskUuid);
    }
}
