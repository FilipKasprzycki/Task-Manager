package com.example.taskmanager.task;

import com.example.taskmanager.task.entity.TaskApiResponse;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

public class TaskService {

    @Inject
    private TaskCreateService taskCreateService;

    @Inject
    private TaskGetService taskGetService;

    @Inject
    private TaskDeleteService taskDeleteService;

    public void create(TaskCreateApiRequest request) {
        taskCreateService.create(request);
    }

    public TaskApiResponse get(UUID taskUuid) {
        return taskGetService.get(taskUuid);
    }

    public List<TaskApiResponse> getAll() {
        return taskGetService.getAll();
    }

    public void delete(UUID uuid) {
        taskDeleteService.delete(uuid);
    }
}
