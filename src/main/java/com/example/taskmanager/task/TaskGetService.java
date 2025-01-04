package com.example.taskmanager.task;

import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.TaskManager;
import com.example.taskmanager.task.entity.TaskApiResponse;
import com.example.taskmanager.task.exception.InvalidDateFormatException;
import com.example.taskmanager.utils.DateConverter;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

class TaskGetService {

    @Inject
    private TaskManager taskManager;

    @Inject
    private TaskMapper taskMapper;

    @Inject
    private DateConverter dateConverter;

    TaskApiResponse get(User user, UUID taskUuid) {
        Task task = taskManager.findByUuidAndUser(taskUuid, user)
                .orElseThrow(() -> new NotFoundException(String.format("Not found task [UUID: %s] for user [UUID: %s]", taskUuid, user.getUuid())));

        return taskMapper.map(task);
    }

    List<TaskApiResponse> getAll(User user, Boolean isCompleted, String deadlineFrom, String deadlineTo) {
        LocalDateTime convertedDeadlineFrom;
        LocalDateTime convertedDeadlineTo;

        try {
            convertedDeadlineFrom = dateConverter.toLocalDateTime(deadlineFrom);
            convertedDeadlineTo = dateConverter.toLocalDateTime(deadlineTo);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(e.getMessage());
        }
        List<Task> tasks = taskManager.findByUserAndFilter(user, isCompleted, convertedDeadlineFrom, convertedDeadlineTo);

        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }
}
