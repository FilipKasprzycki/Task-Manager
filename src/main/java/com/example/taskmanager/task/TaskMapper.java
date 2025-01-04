package com.example.taskmanager.task;

import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.task.entity.TaskApiResponse;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;

class TaskMapper {

    Task map(TaskCreateApiRequest request, User user) {
        Task task = new Task();
        task.setUser(user);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setCompleted(request.getIsCompleted());
        return task;
    }

    TaskApiResponse map(Task task) {
        return TaskApiResponse.builder()
                .uuid(task.getUuid())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .createdAt(task.getCreatedAt())
                .isCompleted(task.isCompleted())
                .build();
    }
}
