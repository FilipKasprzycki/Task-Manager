package com.example.taskmanager.task.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateApiRequest {

    @NotBlank(message = "title is required")
    @Size(max = 10_000, message = "title is too long")
    private String title;

    @Size(max = 10_000, message = "description is too long")
    private String description;

    private LocalDateTime deadline;

    @NotNull
    private Boolean isCompleted;
}
