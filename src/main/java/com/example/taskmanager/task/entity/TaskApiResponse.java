package com.example.taskmanager.task.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class TaskApiResponse {

    private UUID uuid;

    private String title;

    private String description;

    private LocalDateTime deadline;

    private LocalDateTime createdAt;

    private boolean isCompleted;
}
