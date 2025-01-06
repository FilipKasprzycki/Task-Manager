package com.example.taskmanager.task;

import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.task.entity.TaskApiResponse;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    private final TaskMapper sut = new TaskMapper();

    @Test
    void mapToTask() {
        // given
        User user = new User();
        user.setUuid(UUID.fromString("56e21a0f-1a54-4d7f-9ee3-d81ef45633ed"));

        TaskCreateApiRequest request = new TaskCreateApiRequest();
        request.setTitle("Write test");
        request.setDescription("Use jUnit");
        request.setDeadline(LocalDateTime.of(2001, 1, 1, 1, 1));
        request.setIsCompleted(false);

        Task expected = new Task();
        expected.setUser(user);
        expected.setTitle("Write test");
        expected.setDescription("Use jUnit");
        expected.setDeadline(LocalDateTime.of(2001, 1, 1, 1, 1));
        expected.setCompleted(false);

        // when
        Task actual = sut.map(request, user);

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void mapToTaskApiResponse() {
        // given
        Task task = new Task();
        task.setTitle("Write test");
        task.setDescription("Use jUnit");
        task.setDeadline(LocalDateTime.of(2001, 1, 1, 1, 1));
        task.setCompleted(false);
        task.setUuid(UUID.fromString("f26473fc-1981-4fbe-91e2-30a405d9d35e"));

        TaskApiResponse expected = TaskApiResponse.builder()
                .title("Write test")
                .description("Use jUnit")
                .deadline(LocalDateTime.of(2001, 1, 1, 1, 1))
                .isCompleted(false)
                .uuid(UUID.fromString("f26473fc-1981-4fbe-91e2-30a405d9d35e"))
                .build();

        // when
        TaskApiResponse actual = sut.map(task);

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}